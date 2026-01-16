package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.SkillTreeProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillTreePosPacket {

    private final String skillName;
    private final int nodeX;
    private final int nodeY;

    public SkillTreePosPacket(String name, int x, int y) {
        this.skillName = name;
        this.nodeX = x;
        this.nodeY = y;
    }

    public static void encode(SkillTreePosPacket msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.skillName);
        buffer.writeInt(msg.nodeX);
        buffer.writeInt(msg.nodeY);
    }

    public static SkillTreePosPacket decode(FriendlyByteBuf buffer) {
        return new SkillTreePosPacket(buffer.readUtf(),buffer.readInt(),buffer.readInt());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isServer()) {
                // クライアント→サーバー
                ServerPlayer player = context.getSender();

                if (player != null) {
                    player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                        cap.setSkillPos(skillName, nodeX, nodeY);
                    });
                }

            } else if (context.getDirection().getReceptionSide().isClient()) {
                // サーバー→クライアント
                ClientAccess.skillTreePos(skillName,nodeX,nodeY);
            }
        });
        return true;
    }
}
