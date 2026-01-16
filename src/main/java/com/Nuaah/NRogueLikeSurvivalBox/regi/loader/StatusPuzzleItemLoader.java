package com.Nuaah.NRogueLikeSurvivalBox.regi.loader;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.BagLootTableData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleItemData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class StatusPuzzleItemLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    public static final StatusPuzzleItemLoader INSTANCE = new StatusPuzzleItemLoader();

    public static final Map<String, StatusPuzzleItemData> STP_ITEM_DATA = new HashMap<>();

    public StatusPuzzleItemLoader() {
        super(GSON, "status_puzzle_items");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        STP_ITEM_DATA.clear();

        System.out.println("[STP_ITEM_DATA] reload size = "
                + jsonMap.size());
        System.out.println(jsonMap.keySet());

        jsonMap.forEach((location, json) -> {
            try {
                StatusPuzzleItemData data = GSON.fromJson(json, StatusPuzzleItemData.class);
                STP_ITEM_DATA.put(location.getPath(), data);
            } catch (Exception e) {
                System.err.println("Failed to parse json: " + location + " - " + e.getMessage());
            }
        });
    }
}
