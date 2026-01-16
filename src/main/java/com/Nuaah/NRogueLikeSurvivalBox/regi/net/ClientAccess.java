package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.regi.DeathScreenHandler;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoulProvider;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.SkillTreeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

public class ClientAccess {

    public static void updateResultSoul(int result) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(s -> {
                s.setLifetimeSoul(result);
            });
        }
    }

    public static void updateSoul(int newSoul) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(s -> {
                s.setSoul(newSoul);
            });
        }
    }

    public static void setResultSoul(int resultSoul){
        DeathScreenHandler.setResultSoul(resultSoul);
    }

    public static void updateKillCount(int killCount,int level) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(s -> {
                s.setKillCount(killCount);
                s.setKillLevels(level);
            });
        }
    }

    public static void skillTree(String skillName,float skillValue){
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null) {
            player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                cap.addSkill(skillName, skillValue);
            });
        }
    }

    public static void skillTreePos(String skillName,int nodeX,int nodeY){
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null) {
            player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                cap.setSkillPos(skillName, nodeX, nodeY);
            });
        }
    }

    public static void distanceDifficulty(int entityId, int tier, int entityLevel) {

        if (Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity != null) {
                entity.getPersistentData().putInt("DifficultyTier", tier);
                entity.getPersistentData().putInt("Level", entityLevel);
            }
        }
    }
}
