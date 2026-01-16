package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.gui.container.StatusPuzzleManu;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.PlayerStatusPuzzleData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.PlayerStatusPuzzleProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class StatusPuzzleOpenPacket {

    public StatusPuzzleOpenPacket(){}

    public static void encode(StatusPuzzleOpenPacket msg, FriendlyByteBuf buffer) {}
    public static StatusPuzzleOpenPacket decode(FriendlyByteBuf buffer) { return new StatusPuzzleOpenPacket(); }

    public static void handle(StatusPuzzleOpenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                NetworkHooks.openScreen(player, new SimpleMenuProvider((id, inv, p) -> {
                    var cap = p.getCapability(PlayerStatusPuzzleProvider.AUGMENT_INVENTORY).orElse(new PlayerStatusPuzzleData(player));
                    return new StatusPuzzleManu(id, inv, cap);
                }, Component.empty()));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
