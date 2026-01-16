package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.regi.SkillAttributeModifier;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.SkillTreeProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillTreePacket {

    private final String skillName;
    private final float skillValue;

    public SkillTreePacket(String name,float value) {
        this.skillName = name;
        this.skillValue = value;
    }

    public static void encode(SkillTreePacket msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.skillName);
        buffer.writeFloat(msg.skillValue);
    }

    public static SkillTreePacket decode(FriendlyByteBuf buffer) {
        return new SkillTreePacket(buffer.readUtf(),buffer.readFloat());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            if (context.getDirection().getReceptionSide().isServer()) {
                // クライアント→サーバー
                ServerPlayer player = context.getSender();

                if (player != null) {

                    player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                        cap.addSkill(skillName, skillValue);

                        SkillAttributeModifier.checkSkill(player,skillName,cap.getSkill().get(skillName));  //スキル属性付与
                    });
                }

            } else if (context.getDirection().getReceptionSide().isClient()) {
                // サーバー→クライアント
                ClientAccess.skillTree(skillName,skillValue);
            }
        });
        return true;
    }
}
