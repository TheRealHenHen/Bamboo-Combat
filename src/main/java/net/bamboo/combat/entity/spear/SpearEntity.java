package net.bamboo.combat.entity.spear; //By TheRealHenHen

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.bamboo.combat.item.BambooItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpearEntity extends PersistentProjectileEntity {

    private static final TrackedData<Byte> LOYALTY = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static EntityType<SpearEntity> entityType = SpearEntityTypes.BAMBOO_SPEAR;
    private ItemStack defaultItem = new ItemStack(BambooItems.BAMBOO_SPEAR);
    private Entity owner = this.getOwner();
    private World world = owner.getWorld();
    private int entitiesDamaged = 0;
    private int fireTicks = 0;
    private int returnTimer;
    private int burnTicks;
    public float throwDamage;
    private float dragInWater;
    private boolean dealtDamage = false;
    @Nullable
    private IntOpenHashSet piercedEntities;
    @Nullable
    private List<Entity> piercingKilledEntities;

    public SpearEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpearEntity(World world, LivingEntity owner, float throwDamage, float dragInWater, int burnTicks, ItemStack defaultItem, EntityType<SpearEntity> entityType) {
        super(entityType, owner, world);
        this.burnTicks = burnTicks;
        this.dragInWater = dragInWater;
        this.throwDamage = throwDamage + 1;
        this.defaultItem = defaultItem.copy();
        this.dataTracker.set(ENCHANTED, defaultItem.hasGlint());
        this.dataTracker.set(LOYALTY, (byte) EnchantmentHelper.getLoyalty(defaultItem));
        SpearEntity.entityType = entityType;
    }

    @Environment(EnvType.CLIENT)
    public SpearEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(entityType, world);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setId(id);
        setUuid(uuid);
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LOYALTY, (byte) 0);
        this.dataTracker.startTracking(ENCHANTED, false);
    }

    @Override
    protected ItemStack asItemStack() {
        return defaultItem;
    }

    @Override
    protected float getDragInWater() {
        return dragInWater;
    }

    @Override
    public void tick() {

        if (this.inGroundTime > 4 || isCritical() && throwDamage - entitiesDamaged < 1 || !isCritical() && entitiesDamaged > 0) {
            this.dealtDamage = true;
        }

        byte loyalty = this.dataTracker.get(LOYALTY);

        if (loyalty > 0 && (this.dealtDamage || this.isNoClip()) && owner != null) {
            
            if (!this.isOwnerAlive()) {
                if (!world.isClient && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1f);
                }
                this.discard();
            } else {
                this.setNoClip(true);
                Vec3d vec3d = owner.getEyePos().subtract(this.getPos());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double) loyalty, this.getZ());
                if (world.isClient) {
                    this.lastRenderY = this.getY();
                }
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(0.05 * (double) loyalty)));
                if (this.returnTimer == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0f, 1.0f);
                }
                ++this.returnTimer;
            }

        }

        if (!world.isClient && isOnFire() && entityType != SpearEntityTypes.NETHERITE_BAMBOO_SPEAR) {
            if (fireTicks == burnTicks) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    dropStack(this.asItemStack(), 0.1f);
                }
                playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.5F, 2);
                fireTicks = 0;
                discard();
            }
            fireTicks++;
        }

        super.tick();
    }

    @Override
    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return super.getEntityCollision(currentPosition, nextPosition);
    }

    private void clearPiercingStatus() {
        if (this.piercingKilledEntities != null) {
            this.piercingKilledEntities.clear();
        }
        if (this.piercedEntities != null) {
            this.piercedEntities.clear();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {

        Entity owner = getOwner();
        Entity target = entityHitResult.getEntity();
        DamageSource damageSource = this.getDamageSources().trident(this, owner == null ? this : owner);
        float damage = throwDamage;

        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }
            if (this.piercingKilledEntities == null) {
                this.piercingKilledEntities = Lists.newArrayListWithCapacity(5);
            }
            if (this.piercedEntities.size() < this.getPierceLevel() + 1) {
                this.piercedEntities.add(target.getId());
            } else {
                setPierceLevel((byte) 0);
            }
        }

        if (target instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) target;
            damage = throwDamage + EnchantmentHelper.getAttackDamage(defaultItem, livingEntity.getGroup()) - entitiesDamaged;
        }

        if (target.damage(damageSource, damage - entitiesDamaged)) {
            if (target.getType() == EntityType.ENDERMAN || damage - entitiesDamaged < 1) {
                return;
            }
            if (isOnFire() && !(target.getType() == EntityType.ENDERMAN)) {
                target.setOnFireFor(5);
            }
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) target;
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, owner);
                    EnchantmentHelper.onTargetDamaged((LivingEntity) owner, livingEntity);
                }
                onHit(livingEntity);
            }
        }

        if (entitiesDamaged >= this.getPierceLevel()) {
            setVelocity(getVelocity().multiply(-0.01, -0.1, -0.01));
        }

        playSound(SoundEvents.ITEM_TRIDENT_HIT, 2F, 1F);
        entitiesDamaged++;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.isOwner(player) || this.getOwner() == null) {
            super.onPlayerCollision(player);
        }
    }

    @Override
    public void age() {
        byte i = this.dataTracker.get(LOYALTY);
        if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED || i <= 0) {
            super.age();
        }
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
        if (movementType != MovementType.SELF && this.shouldFall()) {
            inGround = false;
            Vec3d vec3d = this.getVelocity();
            this.setVelocity(vec3d.multiply(this.random.nextFloat() * 0.3f, this.random.nextFloat() * 0.3f, this.random.nextFloat() * 0.3f));
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Bamboo_Spear", defaultItem.writeNbt(new NbtCompound()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Bamboo_Spear", 10)) {
            defaultItem = ItemStack.fromNbt(nbt.getCompound("Bamboo_Spear"));
        }
        this.dataTracker.set(LOYALTY, (byte) EnchantmentHelper.getLoyalty(defaultItem));
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.clearPiercingStatus();
    }

    @Override
    protected boolean canHit(Entity entity) {
        return super.canHit(entity) && (this.piercedEntities == null || !this.piercedEntities.contains(entity.getId()));
    }

    public boolean isEnchanted() {
        return dataTracker.get(ENCHANTED);
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity == null || !entity.isAlive()) {
            return false;
        }
        return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
        }

    }

    private boolean shouldFall() {
        return inGround && this.world.isSpaceEmpty(new Box(this.getPos(), this.getPos()).expand(0.06));
    }

}