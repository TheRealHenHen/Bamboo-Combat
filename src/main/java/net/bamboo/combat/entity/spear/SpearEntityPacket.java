package net.bamboo.combat.entity.spear; //By TheRealHenHen

import java.util.UUID;

import net.bamboo.combat.BambooCombat;
import net.bamboo.combat.item.BambooItems;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public record SpearEntityPacket(double x, double y, double z, int entityId, UUID entityUuid) implements CustomPayload {

    private static Identifier spearId = Registries.ITEM.getId(BambooItems.BAMBOO_SPEAR);

    public static final CustomPayload.Id<SpearEntityPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of(BambooCombat.MODID, spearId.getPath()));
    public static final PacketCodec<RegistryByteBuf, SpearEntityPacket> PACKET_CODEC = PacketCodec.of(SpearEntityPacket::encode, SpearEntityPacket::decode);
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

    public static void encode(SpearEntityPacket packet, RegistryByteBuf buf) {
        buf.writeDouble(packet.x);
        buf.writeDouble(packet.y);
        buf.writeDouble(packet.z);
        buf.writeInt(packet.entityId);
        buf.writeUuid(packet.entityUuid);
    }

    public static SpearEntityPacket decode(RegistryByteBuf buf) {
        return new SpearEntityPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readUuid());
    }

    public static void initializePacket() {
        PayloadTypeRegistry.playS2C().register(SpearEntityPacket.PACKET_ID, SpearEntityPacket.PACKET_CODEC);
    }

}