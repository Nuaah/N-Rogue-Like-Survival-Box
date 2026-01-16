package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerHealthSyncData implements PlayerHealthSync{

    private float health = 20;

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putFloat("Health", this.health);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.health = nbt.getFloat("Health");
    }
}
