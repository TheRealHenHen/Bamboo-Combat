package net.bamboo.combat.item.spear; //By TheRealHenHen

import java.util.Random;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.bamboo.combat.config.SpearProperties;
import net.bamboo.combat.entity.spear.SpearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SpearItem
extends ToolItem
implements Vanishable {

    Random random = new Random();
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
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
        super(toolMaterial, settings);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", properties.attackDamage - 1, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", properties.attackSpeed - 4, EntityAttributeModifier.Operation.ADDITION));

        attributeModifiers = builder.build();
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
    
    @Override
    public boolean canMine(BlockState pe, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0f) {
            stack.damage(3, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
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
         
        if (!(livingEntity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity user = (PlayerEntity)livingEntity;
        int i = getMaxUseTime(itemStack) - remainingUseTicks;
        if (i < throwDelay) {
            return;
        }

        if (!world.isClient) {          

            itemStack.damage(durabilityDecreaseAfterThrown, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
            int piercingEnchantmentLevel = EnchantmentHelper.getLevel(Enchantments.PIERCING, itemStack);
            SpearEntity spearEntity = new SpearEntity(world, user, attackDamage, dragInWater, burnTicks, throwDamageDecreaseAfterPierce, itemStack, entityType);
            spearEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, throwDistance, 0.1F);
            spearEntity.setCritical(this.isCritical(user));
            
            if (this.canPierce) {
                spearEntity.setPierceLevel((byte) piercingEnchantmentLevel);
            }
            
            if (this.isCritical(user)) {
                spearEntity.throwDamage += (attackDamage * random.nextFloat()) + 0.3F;

                if (this.canPierce) {
                    spearEntity.setPierceLevel((byte) (pierceLevel + spearEntity.getPierceLevel()));
                }
            }
            
            world.spawnEntity(spearEntity);
            world.playSoundFromEntity(null, spearEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 0.5F, 1F);

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
}
