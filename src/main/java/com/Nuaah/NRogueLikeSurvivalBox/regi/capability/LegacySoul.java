package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.nbt.CompoundTag;

public interface LegacySoul {
    int getSoul();
    void setSoul(int soul);
    void addSoul(int soul);

    void setKillCount(int count);
    void addKillCount(int count);
    int getKillCount();

    void setKillLevels(int count);
    void addKillLevels(int count);
    int getKillLevels();

    void setLifetimeSoul(int soul);
    int getLifetimeSoul();

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}
