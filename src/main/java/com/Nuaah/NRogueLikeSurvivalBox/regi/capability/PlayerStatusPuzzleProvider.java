package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PlayerStatusPuzzleProvider implements ICapabilitySerializable<CompoundTag> {

    public static Capability<PlayerStatusPuzzleData> AUGMENT_INVENTORY = CapabilityManager.get(new CapabilityToken<>() {});

    private final net.minecraft.world.entity.player.Player player; // プレイヤーを保持
    private PlayerStatusPuzzleData backend = null;
    private final LazyOptional<PlayerStatusPuzzleData> optional = LazyOptional.of(this::createInventory);

    public PlayerStatusPuzzleProvider(net.minecraft.world.entity.player.Player player) {
        this.player = player;
    }

    private PlayerStatusPuzzleData createInventory() {
        if (backend == null) backend = new PlayerStatusPuzzleData(player);
        return backend;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == AUGMENT_INVENTORY) return optional.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createInventory().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createInventory().deserializeNBT(nbt);
    }
}
