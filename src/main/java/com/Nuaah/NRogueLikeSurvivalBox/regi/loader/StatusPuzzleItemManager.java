package com.Nuaah.NRogueLikeSurvivalBox.regi.loader;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleItemData;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StatusPuzzleItemManager {
    private static Map<String, StatusPuzzleItemData> serverData = new HashMap<>();
    private static Map<String, StatusPuzzleItemData> clientData = new HashMap<>();

    public static void loadDataOnServer() {
        // ConstituentsJsonLoaderを使ってJSONを読み込む
        Map<String, StatusPuzzleItemData> loadedData = StatusPuzzleItemLoader.STP_ITEM_DATA;

        serverData.clear();
        if (loadedData != null) {
            serverData.putAll(loadedData);
        }
        // デバッグ用: サーバーログにデータが読み込まれたか出力
        System.out.println("Status puzzle item Manager: Loaded " + serverData.size() + " entries on the server.");
    }

    public static Map<String, StatusPuzzleItemData> getServerData() {
        return Collections.unmodifiableMap(serverData);
    }

    public static void setClientData(Map<String, StatusPuzzleItemData> data) {
        clientData.clear();
        if (data != null) clientData.putAll(data);
    }

    public static Map<String, StatusPuzzleItemData> getClientData() {
        return Collections.unmodifiableMap(clientData);
    }
}
