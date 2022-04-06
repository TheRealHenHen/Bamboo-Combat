package net.bamboo.combat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.bamboo.combat.entity.SpearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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

    private int spearPierceLevel;
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private boolean fireProof;
    private float throwDistance;
    private float attackDamage;
    private int throwDelay;

    public SpearItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, float throwDistance, int throwDelay, boolean fireProof, int spearPierceLevel) {
        super(toolMaterial, new Item.Settings().group(ItemGroup.COMBAT));

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)attackDamage - 1, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)attackSpeed - 4, EntityAttributeModifier.Operation.ADDITION));

        attributeModifiers = builder.build();
        this.fireProof = fireProof;
        this.attackDamage = attackDamage - 1;
        this.throwDistance = throwDistance;
        this.throwDelay = throwDelay;
    }

    @Override
	public boolean isFireproof() {
        return fireProof;
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
        user.setCurrentHand(hand);

		if (itemStack.getDamage() > itemStack.getMaxDamage() - 2) 
           return TypedActionResult.fail(itemStack);

        return TypedActionResult.consume(itemStack);
	}

    @Override
    public void onStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int remainingUseTicks) {

        PlayerEntity user = (PlayerEntity)livingEntity;
        Hand hand = user.getActiveHand();
        itemStack = user.getStackInHand(hand);
         
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        
        int i = getMaxUseTime(itemStack) - remainingUseTicks;
        if (i < throwDelay) {
            return;
        }

        if (!world.isClient) {          

            itemStack.damage(2, user, p -> p.sendToolBreakStatus(user.getActiveHand()));  
            SpearEntity spear = new SpearEntity(world, user, attackDamage, fireProof, spearPierceLevel, itemStack);

            if (!spear.getOwner().isOnGround()) {
                spear.pierceLevel++;
                throwDistance += 0.2;
                SpearEntity.critical = true;
            }

            if (!spear.getOwner().isSprinting()) {
                spear.pierceLevel = 0;
                SpearEntity.critical = false;
            } else {
                spear.throwDamage++;
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

}