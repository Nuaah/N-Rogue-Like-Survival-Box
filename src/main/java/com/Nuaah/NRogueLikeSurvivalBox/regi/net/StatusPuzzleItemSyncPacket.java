package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleItemData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.StatusPuzzleItemLoader;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.StatusPuzzleItemManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class StatusPuzzleItemSyncPacket {
    private final Map<String, StatusPuzzleItemData> data;
    private static final Gson GSON = new Gson();

    public StatusPuzzleItemSyncPacket(Map<String, StatusPuzzleItemData> data) {
        this.data = data;
    }

    public static void encode(StatusPuzzleItemSyncPacket msg, FriendlyByteBuf buf) {
        // JSON にして送る（簡単）。大きくなる場合は別途圧縮/バイナリ化検討。
        String json = GSON.toJson(msg.data);
        buf.writeUtf(json);
    }

    public static StatusPuzzleItemSyncPacket decode(FriendlyByteBuf buf) {
        String json = buf.readUtf(32767);
        Type type = new TypeToken<Map<String, StatusPuzzleItemData>>() {}.getType();
        Map<String, StatusPuzzleItemData> map = GSON.fromJson(json, type);
        if (map == null) map = new HashMap<>();
        return new StatusPuzzleItemSyncPacket(map);
    }

    public static void handle(StatusPuzzleItemSyncPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        System.out.println("StatusPuzzleItemSyncPacket: Received packet. Data size: " + msg.data.size());

        ctx.enqueueWork(() -> {
            System.out.println("StatusPuzzleItemSyncPacket: Applying data to ConstituentsManager.");

            try {
                StatusPuzzleItemManager.setClientData(msg.data);

                StatusPuzzleItemLoader.STP_ITEM_DATA.clear();
                StatusPuzzleItemLoader.STP_ITEM_DATA.putAll(msg.data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ctx.setPacketHandled(true);
    }
}
