package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DistanceDifficultyPacket {
    private final int entityId;
    private final int tier;
    public final int entityLevel; // 追加

    public DistanceDifficultyPacket(int entityId, int tier,int entityLevel) {
        this.entityId = entityId;
        this.tier = tier;
        this.entityLevel = entityLevel;
    }

    // バッファへの書き込み（送信時）
    public static void encode(DistanceDifficultyPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.entityId);
        buffer.writeInt(msg.tier);
        buffer.writeInt(msg.entityLevel);
    }

    // バッファからの読み込み（受信時）
    public static DistanceDifficultyPacket decode(FriendlyByteBuf buffer) {
        return new DistanceDifficultyPacket(buffer.readInt(), buffer.readInt(),buffer.readInt());
    }

    // 受信時の処理（クライアント側で実行）
    public static void handle(DistanceDifficultyPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // ラムダ式の中にも直接 Minecraft と書かず、外部の「ClientAccess」を呼ぶだけにする
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientAccess.distanceDifficulty(msg.entityId, msg.tier, msg.entityLevel)
            );
        });

        context.setPacketHandled(true);
    }
}
