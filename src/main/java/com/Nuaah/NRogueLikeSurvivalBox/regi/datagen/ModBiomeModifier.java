package com.Nuaah.NRogueLikeSurvivalBox.regi.datagen;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
public class ModBiomeModifier {

    public static final ResourceKey<BiomeModifier> WOOD_KID_KEY = createKey("wood_kid");
    public static final ResourceKey<BiomeModifier> ROCKY_KEY = createKey("rocky");
    public static final ResourceKey<BiomeModifier> DOODUS_KEY = createKey("doodus");
    public static final ResourceKey<BiomeModifier> SPRING_WEED_KEY = createKey("spring_weed");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        context.register(WOOD_KID_KEY, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
            biomes.getOrThrow(BiomeTags.IS_FOREST), // 出現させたいバイオームタグ
            List.of(new MobSpawnSettings.SpawnerData(
                NRogueLikeSurvivalBoxEntities.WOOD_KID.get(),
                80, // weight: 出現頻度
                1,  // minCount
                4   // maxCount
            ))
        ));

        context.register(ROCKY_KEY, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), // 出現させたいバイオームタグ
                List.of(new MobSpawnSettings.SpawnerData(
                        NRogueLikeSurvivalBoxEntities.ROCKY.get(),
                        80, // weight: 出現頻度
                        1,  // minCount
                        4   // maxCount
                ))
        ));

        context.register(DOODUS_KEY, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.HAS_DESERT_PYRAMID), // 出現させたいバイオームタグ
                List.of(new MobSpawnSettings.SpawnerData(
                        NRogueLikeSurvivalBoxEntities.DOODUS.get(),
                        120, // weight: 出現頻度
                        1,  // minCount
                        1   // maxCount
                ))
        ));

        context.register(SPRING_WEED_KEY, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_SAVANNA), // 出現させたいバイオームタグ
                List.of(new MobSpawnSettings.SpawnerData(
                        NRogueLikeSurvivalBoxEntities.SPRING_WEED.get(),
                        100, // weight: 出現頻度
                        2,  // minCount
                        4   // maxCount
                ))
        ));
    }

    public static ResourceKey<BiomeModifier> createKey(String name){
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID,name));
    }
}
