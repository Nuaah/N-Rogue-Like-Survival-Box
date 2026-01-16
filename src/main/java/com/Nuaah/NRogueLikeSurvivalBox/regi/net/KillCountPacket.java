package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KillCountPacket {

    public final int killCount;
    public final int killLevel;

    public KillCountPacket(int killCount,int level) {
        this.killCount = killCount;
        this.killLevel = level;
    }

    public static void encode(KillCountPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.killCount);
        buffer.writeInt(msg.killLevel);
    }

    // バッファからの読み込み（受信時）
    public static KillCountPacket decode(FriendlyByteBuf buffer) {
        return new KillCountPacket(buffer.readInt(),buffer.readInt());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // クライアント側のプレイヤーにデータをセットする
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ClientAccess.updateKillCount(killCount,killLevel);
            });
        });
        return true;
    }
}
