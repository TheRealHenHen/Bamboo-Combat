package net.bamboo.combat.item.spear; //By TheRealHenHen

import java.util.Random;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.bamboo.combat.entity.spear.SpearEntity;
import net.bamboo.combat.entity.spear.SpearEntityTypes;
import net.minecraft.block.BlockState;
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
    public Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    public EntityType<SpearEntity> entityType;
    public float throwDistance;
    public float attackDamage;
    public float dragInWater;
    public int pierceLevel;
    public int throwDelay;
    public int burnTicks;

    public SpearItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, float throwDistance, float dragInWater, int throwDelay, int pierceLevel, int burnTicks, EntityType<SpearEntity> entityType) {
        super(toolMaterial, new Item.Settings());

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage - 1, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed - 4, EntityAttributeModifier.Operation.ADDITION));

        attributeModifiers = builder.build();
        this.burnTicks = burnTicks;
        this.throwDelay = throwDelay;
        this.entityType = entityType;
        this.dragInWater = dragInWater; 
        this.pierceLevel = pierceLevel;
        this.attackDamage = attackDamage - 1;
        this.throwDistance = throwDistance;
    }

    @Override
	public boolean isFireproof() {
        return entityType == SpearEntityTypes.NETHERITE_BAMBOO_SPEAR ? true : false;
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
        
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 2) {
            return TypedActionResult.fail(itemStack);
        }

        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
	}

    @Override
    public void onStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int remainingUseTicks) {

        PlayerEntity user = (PlayerEntity)livingEntity;
        itemStack.damage(2, user, e -> e.sendToolBreakStatus(user.getActiveHand()));
         
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        
        int i = getMaxUseTime(itemStack) - remainingUseTicks;
        if (i < throwDelay) {
            return;
        }

        if (!world.isClient) {          
            
            SpearEntity spear = new SpearEntity(world, user, attackDamage, dragInWater, burnTicks, itemStack, entityType);
            itemStack.damage(2, user, p -> p.sendToolBreakStatus(user.getActiveHand()));

            spear.setCritical(isCritical(user));

            if (isCritical(user)) {
                spear.throwDamage += attackDamage * random.nextFloat(0.3F);
                spear.setPierceLevel((byte) pierceLevel);
            } else {
                spear.setPierceLevel((byte) 0);
            }
            
            spear.setOwner(user);
            spear.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, throwDistance, 0.1F);
            world.spawnEntity(spear);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 0.5F, 1F);

            if (user.getAbilities().creativeMode) {
                spear.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
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
        if ((user.isSprinting() && !user.isOnGround()) || (user.hasVehicle() && !user.getRootVehicle().isOnGround())) {
            return true;
        }
        return false;
    }

}