package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoulProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LegacySoulPacket {

    public final int soul;

    public LegacySoulPacket(int soul) {
        this.soul = soul;
    }

    public static void encode(LegacySoulPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.soul);
    }

    // バッファからの読み込み（受信時）
    public static LegacySoulPacket decode(FriendlyByteBuf buffer) {
        return new LegacySoulPacket(buffer.readInt());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isServer()) {
                // クライアント→サーバー
                ServerAccess.updateSoul(soul,context);

            } else if (context.getDirection().getReceptionSide().isClient()) {
                // サーバー→クライアント
                ClientAccess.updateSoul(soul);
            }
        });
        return true;
    }

    public class ServerAccess {
        public static void updateSoul(int newSoul,NetworkEvent.Context context) {
            net.minecraft.server.level.ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(cap -> {
                    cap.setSoul(newSoul);
                });
            }
        }
    }
}
