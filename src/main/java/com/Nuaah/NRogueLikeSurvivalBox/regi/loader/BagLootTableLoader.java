package com.Nuaah.NRogueLikeSurvivalBox.regi.loader;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.BagLootTableData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class BagLootTableLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    public static final BagLootTableLoader INSTANCE = new BagLootTableLoader();

    public static final Map<String, BagLootTableData> BAG_LOOT_TABLE_DATA = new HashMap<>();

    public BagLootTableLoader() {
        super(GSON, "bag_loot_table");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        BAG_LOOT_TABLE_DATA.clear();

        System.out.println("[BAG_LOOT_TABLE] reload size = "
                + jsonMap.size());
        System.out.println(jsonMap.keySet());

        jsonMap.forEach((location, json) -> {
            try {
                BagLootTableData data = GSON.fromJson(json, BagLootTableData.class);
                BAG_LOOT_TABLE_DATA.put(location.getPath(), data);
            } catch (Exception e) {
                System.err.println("Failed to parse json: " + location + " - " + e.getMessage());
            }
        });
    }
}
