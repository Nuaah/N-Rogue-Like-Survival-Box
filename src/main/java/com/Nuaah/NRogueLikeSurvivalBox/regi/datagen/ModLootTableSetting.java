package com.Nuaah.NRogueLikeSurvivalBox.regi.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class ModLootTableSetting {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(
            output,
            Set.of(), // バニラのルートテーブルを上書きしない場合は空のSet
            List.of(
                new LootTableProvider.SubProviderEntry(
                    ModEntityLootTables::new,
                    LootContextParamSets.ENTITY
                )
            )
        );
    }
}
