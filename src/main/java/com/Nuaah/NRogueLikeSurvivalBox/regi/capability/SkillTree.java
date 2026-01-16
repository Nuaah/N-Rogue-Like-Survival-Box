package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SkillTree {
    Map<String,Float> skill = new HashMap<>();
    Map<String, List<Vec2>> skillPos = new HashMap<>();

    void setSkill(String skillName, float value);

    void addSkill(String skillName, float value);

    void setSkillPos(String skillName, int x,int y);

    Map<String,Float> getSkill();
    Map<String, List<Vec2>> getSkillPos();

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}
