package net.bamboo.combat.entity; //By TheRealHenHen

import java.util.UUID;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;
import net.bamboo.combat.BambooCombat;
import net.bamboo.combat.item.BambooItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpearEntity extends PersistentProjectileEntity {

	public static final Identifier SPAWN_PACKET = new Identifier(BambooCombat.MODID, "bamboo_spear");
    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public PickupPermission pickupType;
    private ItemStack defaultItem = new ItemStack(BambooItems.BAMBOO);
    private static EntityType<SpearEntity> entityType;
    private int entitiesDamaged = 0;
    public int pierceLevel;
    public float throwDamage;
    private boolean fireProof;
    public static boolean critical = false;

    public SpearEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public SpearEntity(World world, LivingEntity owner, float throwDamage, boolean fireProof, int pierceLevel, ItemStack defaultItem, EntityType<SpearEntity> entityType) {
		super(entityType, owner, world);
        this.pierceLevel = pierceLevel;
        this.throwDamage = throwDamage + 1;
        this.fireProof = fireProof;
        this.defaultItem = defaultItem.copy();
        this.dataTracker.set(ENCHANTED, defaultItem.hasGlint());
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
        this.dataTracker.startTracking(ENCHANTED, false);
	}
	
	@Override
	public Packet<?> createSpawnPacket() {
		PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());

		packet.writeInt(getId());
		packet.writeUuid(getUuid());

		return ServerPlayNetworking.createS2CPacket(SPAWN_PACKET, packet);
	}

	@Override
    protected ItemStack asItemStack() {
        return defaultItem;
    }

	@Override
    public void tick() {
		if (!isOwnerAlive()) {
			if (!world.isClient && pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
				dropStack(asItemStack(), 0.1f);
			}
		} else {
            setNoClip(true);
            if (world.isClient) {
                lastRenderY = getY();
            }
        }
        if (isOnFire() && !fireProof) {
            kill();
            playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.5F, 2);
        }
        doesRenderOnFire();
        super.tick();
    }

	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) { // checks if the world is client
			this.world.sendEntityStatus(this, (byte)3); // particle?
		}
 
	}

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Override
    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return super.getEntityCollision(currentPosition, nextPosition);
    }

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity owner = getOwner();
		Entity entity = entityHitResult.getEntity();
		DamageSource damageSource = DamageSource.trident(this, owner == null ? this : owner);
        entitiesDamaged++;

		if (entity.damage(damageSource, throwDamage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, owner);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)owner, livingEntity);
                }
                onHit(livingEntity);
            }
        }
       
        if (entitiesDamaged > pierceLevel)
            setVelocity(getVelocity().multiply(-0.01, -0.1, -0.01));

		playSound(SoundEvents.ITEM_TRIDENT_HIT, 2F, 1F);
	}

	private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity == null || !entity.isAlive()) {
            return false;
        }
        return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
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
    protected float getDragInWater() {
        return 2f;
    }

	@Override
    public void age() {
        if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED) {
            super.age();
        }
    }

	@Override
	public boolean isNoClip() {
        return false;
    }

    @Override
    public boolean isCritical() {
        return SpearEntity.critical;
    }

	private boolean shouldFall() {
        return this.inGround && this.world.isSpaceEmpty(new Box(this.getPos(), this.getPos()).expand(0.06));
    }

	private void fall() {
        this.inGround = false;
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.multiply(this.random.nextFloat() * 0.4f, this.random.nextFloat() * 0.4f, this.random.nextFloat() * 0.4f));
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
        if (movementType != MovementType.SELF && this.shouldFall()) {
            this.fall();
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("bamboo_spear", defaultItem.writeNbt(new NbtCompound()));
        nbt.putBoolean("crit", isCritical());
    }

	@Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("bamboo_spear", 10)) {
            defaultItem = ItemStack.fromNbt(nbt.getCompound("bamboo_spear"));
            nbt.putBoolean("crit", isCritical());
        }
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }
    
}