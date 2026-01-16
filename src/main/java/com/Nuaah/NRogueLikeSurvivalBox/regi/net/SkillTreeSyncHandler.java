package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.SkillTreeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class SkillTreeSyncHandler {
    public static void handleSkillTreeSync(SkillTreeSyncPacket msg) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                cap.deserializeNBT(msg.data);
            });
        }
    }
}
