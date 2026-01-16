package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerHealthSyncProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerHealthSync> PLAYER_HEALTH = CapabilityManager.get(new CapabilityToken<>() {});

    private PlayerHealthSync backend = null;
    private final LazyOptional<PlayerHealthSync> optional = LazyOptional.of(this::createPlayerHealthSync);

    private PlayerHealthSync createPlayerHealthSync() {
        if (this.backend == null) {
            this.backend = new PlayerHealthSyncData();
        }
        return this.backend;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_HEALTH ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createPlayerHealthSync().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerHealthSync().deserializeNBT(nbt);
    }
}
