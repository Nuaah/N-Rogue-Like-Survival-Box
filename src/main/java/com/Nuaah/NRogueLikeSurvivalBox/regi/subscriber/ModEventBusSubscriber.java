package com.Nuaah.NRogueLikeSurvivalBox.regi.subscriber;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.DoodusEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.RockyEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.SpringWeedEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.WoodKidEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.model.WoodKidModel;
import com.Nuaah.NRogueLikeSurvivalBox.entity.renderer.NRogueLikeSurvivalBoxRenderers;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import com.Nuaah.NRogueLikeSurvivalBox.regi.datagen.ModGenSetting;
import com.Nuaah.NRogueLikeSurvivalBox.regi.datagen.ModLootTableSetting;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = NRogueLikeSurvivalBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // さきほどのProviderを追加
        generator.addProvider(event.includeServer(),
                new ModGenSetting(output, lookupProvider));

        generator.addProvider(event.includeServer(), ModLootTableSetting.create(output));
    }

    @SubscribeEvent
    public static void registerAttribute(EntityAttributeCreationEvent event){
        event.put(NRogueLikeSurvivalBoxEntities.WOOD_KID.get(), WoodKidEntity.createAttribute().build());
        event.put(NRogueLikeSurvivalBoxEntities.ROCKY.get(), RockyEntity.createAttribute().build());
        event.put(NRogueLikeSurvivalBoxEntities.DOODUS.get(), DoodusEntity.createAttribute().build());
        event.put(NRogueLikeSurvivalBoxEntities.SPRING_WEED.get(), SpringWeedEntity.createAttribute().build());

    }

    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(
                NRogueLikeSurvivalBoxEntities.WOOD_KID.get(),
                SpawnPlacements.Type.ON_GROUND, // 地面にスポーン
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, // 葉っぱ以外の不透過ブロックの上
                Monster::checkMonsterSpawnRules, // モンスターの基本ルール（明るさなど）を適用
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                NRogueLikeSurvivalBoxEntities.ROCKY.get(),
                SpawnPlacements.Type.ON_GROUND, // 地面にスポーン
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, // 葉っぱ以外の不透過ブロックの上
                Monster::checkMonsterSpawnRules, // モンスターの基本ルール（明るさなど）を適用
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                NRogueLikeSurvivalBoxEntities.DOODUS.get(),
                SpawnPlacements.Type.ON_GROUND, // 地面にスポーン
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, // 葉っぱ以外の不透過ブロックの上
                Monster::checkMonsterSpawnRules, // モンスターの基本ルール（明るさなど）を適用
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                NRogueLikeSurvivalBoxEntities.SPRING_WEED.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, // モンスターの基本ルール（明るさなど）を適用
                SpawnPlacementRegisterEvent.Operation.OR
        );
    }
}
