package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlayerHealthSync {
    float health = 20;

    public void setHealth(float health);
    public float getHealth();


    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}
