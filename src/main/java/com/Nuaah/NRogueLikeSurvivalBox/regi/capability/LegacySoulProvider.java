package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LegacySoulProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<LegacySoul> LEGACY_SOUL = CapabilityManager.get(new CapabilityToken<>() {});

    private LegacySoul backend = null;
    private final LazyOptional<LegacySoul> optional = LazyOptional.of(this::createLegacySoul);

    private LegacySoul createLegacySoul() {
        if (this.backend == null) {
            this.backend = new LegacySoulData();
        }
        return this.backend;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == LEGACY_SOUL ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createLegacySoul().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createLegacySoul().deserializeNBT(nbt);
    }
}
