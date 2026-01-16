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

public class SkillTreeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<SkillTree> SKILL_TREE = CapabilityManager.get(new CapabilityToken<>() {});

    private SkillTree backend = null;
    private final LazyOptional<SkillTree> optional = LazyOptional.of(this::createSkillTree);

    private SkillTree createSkillTree() {
        if (this.backend == null) {
            this.backend = new SkillTreeData();
        }
        return this.backend;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == SKILL_TREE ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createSkillTree().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSkillTree().deserializeNBT(nbt);
    }
}
