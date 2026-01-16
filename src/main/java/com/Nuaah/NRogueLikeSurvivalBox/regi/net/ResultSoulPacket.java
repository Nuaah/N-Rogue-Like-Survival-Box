package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResultSoulPacket {

    public final int resultSoul;

    public ResultSoulPacket(int result) {
        this.resultSoul = result;
    }

    public static void encode(ResultSoulPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.resultSoul);
    }

    // バッファからの読み込み（受信時）
    public static ResultSoulPacket decode(FriendlyByteBuf buffer) {
        return new ResultSoulPacket(buffer.readInt());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            // クライアント側のプレイヤーにデータをセットする
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ClientAccess.updateResultSoul(resultSoul);
                ClientAccess.setResultSoul(resultSoul);
            });
        });
        return true;
    }
}
