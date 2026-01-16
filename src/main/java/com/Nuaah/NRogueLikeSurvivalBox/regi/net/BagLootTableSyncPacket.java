package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.BagLootTableData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.BagLootTableLoader;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.BagLootTableManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BagLootTableSyncPacket {
    private final Map<String, BagLootTableData> data;
    private static final Gson GSON = new Gson();

    public BagLootTableSyncPacket(Map<String, BagLootTableData> data) {
        this.data = data;
    }

    public static void encode(BagLootTableSyncPacket msg, FriendlyByteBuf buf) {
        // JSON にして送る（簡単）。大きくなる場合は別途圧縮/バイナリ化検討。
        String json = GSON.toJson(msg.data);
        buf.writeUtf(json);
    }

    public static BagLootTableSyncPacket decode(FriendlyByteBuf buf) {
        String json = buf.readUtf(32767);
        Type type = new TypeToken<Map<String, BagLootTableData>>() {}.getType();
        Map<String, BagLootTableData> map = GSON.fromJson(json, type);
        if (map == null) map = new HashMap<>();
        return new BagLootTableSyncPacket(map);
    }

    public static void handle(BagLootTableSyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        System.out.println("StatusPuzzleItemSyncPacket: Received packet. Data size: " + msg.data.size());

        // クライアントスレッドで処理
        ctx.enqueueWork(() -> {
            System.out.println("StatusPuzzleItemSyncPacket: Applying data to ConstituentsManager.");

            try {
                BagLootTableManager.setClientData(msg.data);

                BagLootTableLoader.BAG_LOOT_TABLE_DATA.clear();
                BagLootTableLoader.BAG_LOOT_TABLE_DATA.putAll(msg.data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ctx.setPacketHandled(true);
    }
}
