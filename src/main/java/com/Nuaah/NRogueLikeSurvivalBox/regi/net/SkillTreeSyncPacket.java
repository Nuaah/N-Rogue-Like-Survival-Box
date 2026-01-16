package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillTreeSyncPacket {

    public final CompoundTag data;

    public SkillTreeSyncPacket(CompoundTag data) { this.data = data; }

    public static void encode(SkillTreeSyncPacket msg, FriendlyByteBuf buf) { buf.writeNbt(msg.data); }
    public static SkillTreeSyncPacket decode(FriendlyByteBuf buf) { return new SkillTreeSyncPacket(buf.readNbt()); }

    public static void handle(SkillTreeSyncPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT, () -> () -> {
                SkillTreeSyncHandler.handleSkillTreeSync(msg);
            });
        });
        supplier.get().setPacketHandled(true);
    }
}
