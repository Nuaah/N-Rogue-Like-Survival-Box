package com.Nuaah.NRogueLikeSurvivalBox.regi.loader;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.BagLootTableData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BagLootTableManager {
    private static Map<String, BagLootTableData> serverData = new HashMap<>();
    private static Map<String, BagLootTableData> clientData = new HashMap<>();

    public static void loadDataOnServer() {
        // ConstituentsJsonLoaderを使ってJSONを読み込む
        Map<String, BagLootTableData> loadedData = BagLootTableLoader.BAG_LOOT_TABLE_DATA;

        serverData.clear();
        if (loadedData != null) {
            serverData.putAll(loadedData);
        }
        // デバッグ用: サーバーログにデータが読み込まれたか出力
        System.out.println("Bag loot table Manager: Loaded " + serverData.size() + " entries on the server.");
    }

    public static Map<String, BagLootTableData> getServerData() {
        return Collections.unmodifiableMap(serverData);
    }

    public static void setClientData(Map<String, BagLootTableData> data) {
        clientData.clear();
        if (data != null) clientData.putAll(data);
    }

    public static Map<String, BagLootTableData> getClientData() {
        return Collections.unmodifiableMap(clientData);
    }
}
