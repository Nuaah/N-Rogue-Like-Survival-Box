package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.Map;

public class LegacySoulData implements LegacySoul{

    private int lifetimeSoul;
    private int legacySoul;
    private int killCount;
    private int killLevels;

    @Override
    public int getSoul() {
        return legacySoul;
    }

    @Override
    public void setSoul(int soul) {
        legacySoul = soul;
    }

    @Override
    public void addSoul(int soul) {
        legacySoul += soul;
    }

    @Override
    public void setKillCount(int count) {
        killCount = count;
    }

    @Override
    public void addKillCount(int count) {
        killCount += count;
    }

    @Override
    public int getKillCount() {
        return killCount;
    }

    @Override
    public void setKillLevels(int count) {
        killLevels = count;
    }

    @Override
    public void addKillLevels(int count) {
        killLevels += count;
    }

    @Override
    public int getKillLevels() {
        return killLevels;
    }

    @Override
    public void setLifetimeSoul(int soul) {
        lifetimeSoul = soul;
    }

    @Override
    public int getLifetimeSoul() {
        return lifetimeSoul;
    }

    @Override
    public CompoundTag serializeNBT() {
        System.out.println("SERIALIZE");
        System.out.println(legacySoul);

        CompoundTag nbt = new CompoundTag();

        nbt.putInt("LegacySoul", legacySoul);
        nbt.putInt("KillCount", killCount);
        nbt.putInt("KillLevels",killLevels);
        nbt.putInt("LifetimeSoul",lifetimeSoul);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        legacySoul = nbt.getInt("LegacySoul");
        killCount = nbt.getInt("KillCount");
        killLevels = nbt.getInt("KillLevels");
        lifetimeSoul = nbt.getInt("LifetimeSoul");
    }
}
