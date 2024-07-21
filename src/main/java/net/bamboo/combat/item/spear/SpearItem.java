package net.bamboo.combat.item.spear; //By TheRealHenHen

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.bamboo.combat.config.SpearProperties;
import net.bamboo.combat.entity.spear.SpearEntity;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.block.BlockState;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SpearItem extends ToolItem implements ProjectileItem {

    Random random = new Random();
    private EntityType<SpearEntity> entityType;
    private boolean canCriticalThrow;
    private boolean canPierce;
    private float throwDistance;
    private float attackDamage;
    private float dragInWater;
    private int throwDelay;
    private int pierceLevel;
    private int burnTicks;
    private int durabilityDecreaseAfterThrown;
    private int throwDamageDecreaseAfterPierce;

    public SpearItem(ToolMaterial toolMaterial, SpearProperties properties,  EntityType<SpearEntity> entityType, Item.Settings settings) {
        super(toolMaterial, settings.attributeModifiers(createAttributeModifiers(properties.attackDamage, properties.attackSpeed)));
        
        this.canCriticalThrow = properties.canCriticalThrow;
        this.canPierce = properties.canPierce;
        this.attackDamage = properties.attackDamage - 1;
        this.burnTicks = properties.burnTicks;
        this.throwDistance = setLimit(properties.throwDistance, 100);
        this.dragInWater = setLimit(properties.dragInWater, 100); 
        this.throwDelay = properties.throwDelay;
        this.pierceLevel = setLimit(properties.pierceLevel, 100);
        this.durabilityDecreaseAfterThrown = properties.durabilityDecreaseAfterThrown;
        this.throwDamageDecreaseAfterPierce = properties.throwDamageDecreaseAfterPierce;
        this.entityType = entityType;
    } 

    public static AttributeModifiersComponent createAttributeModifiers(float attackDamage, float attackSpeed) {
		return AttributeModifiersComponent.builder()
			.add(
				EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage - 1, EntityAttributeModifier.Operation.ADD_VALUE),
				AttributeModifierSlot.MAINHAND
			)
			.add(
				EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed - 4, EntityAttributeModifier.Operation.ADD_VALUE),
				AttributeModifierSlot.MAINHAND
			)
			.build();
	}
    
    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        
        List<RegistryKey<Enchantment>> enchantmentKeys = new ArrayList<>();
        enchantmentKeys.add(Enchantments.UNBREAKING);
        enchantmentKeys.add(Enchantments.MENDING);
        enchantmentKeys.add(Enchantments.LOYALTY);
        enchantmentKeys.add(Enchantments.SHARPNESS);
        enchantmentKeys.add(Enchantments.SMITE);
        enchantmentKeys.add(Enchantments.BANE_OF_ARTHROPODS);
        enchantmentKeys.add(Enchantments.PIERCING);

        return enchantmentKeys.contains(enchantment.getKey().get());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
		return true;
	}
    
    @Override
    public boolean canMine(BlockState pe, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
	public int getEnchantability() {
		return 1;
	}

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

	@Override
	public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, EquipmentSlot.MAINHAND);
	}

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0f) {
            stack.damage(3, miner, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		
		ItemStack itemStack = user.getStackInHand(hand);
        
        if ((itemStack.getDamage() >= itemStack.getMaxDamage() - durabilityDecreaseAfterThrown) && itemStack.getMaxDamage() > 0) {
            return TypedActionResult.fail(itemStack);
        }

        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
	}

    @Override
    public void onStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int remainingUseTicks) {
         
        if (!(livingEntity instanceof PlayerEntity user)) {
            return;
        }

        int i = getMaxUseTime(itemStack, livingEntity) - remainingUseTicks;
        if (i < throwDelay) {
            return;
        }

        RegistryEntry<SoundEvent> soundRegistryEntry = (RegistryEntry<SoundEvent>)EnchantmentHelper.getEffect(itemStack, EnchantmentEffectComponentTypes.TRIDENT_SOUND)
            .orElse(SoundEvents.ITEM_TRIDENT_THROW);

        if (!world.isClient) {          

            itemStack.damage(durabilityDecreaseAfterThrown, user, LivingEntity.getSlotForHand(user.getActiveHand()));
            RegistryEntry<Enchantment> piercingRegistryEntry = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.PIERCING).get();
            int piercingEnchantmentLevel = EnchantmentHelper.getLevel(piercingRegistryEntry, itemStack);
            SpearEntity spearEntity = new SpearEntity(world, user, attackDamage, dragInWater, burnTicks, throwDamageDecreaseAfterPierce, itemStack, entityType);
            spearEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, throwDistance, 0.1F);
            spearEntity.setCritical(this.isCritical(user));
            
            if (this.canPierce) {
                spearEntity.setPierceLevel((byte) piercingEnchantmentLevel);
            }
            
            if (this.isCritical(user)) {
                spearEntity.throwDamage += attackDamage * random.nextFloat(0.3F);

                if (this.canPierce) {
                    spearEntity.setPierceLevel((byte) (pierceLevel + spearEntity.getPierceLevel()));
                }
            }
            
            world.spawnEntity(spearEntity);
            world.playSoundFromEntity(null, spearEntity, soundRegistryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);

            if (user.getAbilities().creativeMode) {
                spearEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            } else {
                user.getInventory().removeOne(itemStack);
            }

        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));

    }

    public EntityType<? extends SpearEntity> getEntityType() {
        return entityType;
    }

    private boolean isCritical(PlayerEntity user) {
        return ((user.isSprinting() && !user.isOnGround()) || (user.hasVehicle() && !user.getRootVehicle().isOnGround())) && canCriticalThrow;
    }

    private int setLimit(int value, int limit) {
        return value > limit ? limit : value;
    }

    private float setLimit(float value, float limit) {
        return value > limit ? limit : value;
    }
    
    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        SpearEntity spearEntity = new SpearEntity(this.entityType, world);
        spearEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return spearEntity;
    }
}
