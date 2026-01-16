package com.Nuaah.NRogueLikeSurvivalBox.regi.net;

import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@SuppressWarnings("removal")
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "main"),
                    () -> PROTOCOL_VERSION,
                    PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals
            );

    private static int packetId = 0;

    public static int nextId() {
        return packetId++;
    }

    public static void register() {
        CHANNEL.messageBuilder(DistanceDifficultyPacket.class,nextId())
                .encoder(DistanceDifficultyPacket::encode)
                .decoder(DistanceDifficultyPacket::decode)
                .consumerMainThread(DistanceDifficultyPacket::handle)
                .add();

        CHANNEL.messageBuilder(StatusPuzzleOpenPacket.class,nextId())
                .encoder(StatusPuzzleOpenPacket::encode)
                .decoder(StatusPuzzleOpenPacket::decode)
                .consumerMainThread(StatusPuzzleOpenPacket::handle)
                .add();

        CHANNEL.messageBuilder(LegacySoulPacket.class,nextId())
                .encoder(LegacySoulPacket::encode)
                .decoder(LegacySoulPacket::decode)
                .consumerMainThread(LegacySoulPacket::handle)
                .add();

        CHANNEL.messageBuilder(KillCountPacket.class,nextId())
                .encoder(KillCountPacket::encode)
                .decoder(KillCountPacket::decode)
                .consumerMainThread(KillCountPacket::handle)
                .add();

        CHANNEL.messageBuilder(ResultSoulPacket.class,nextId())
                .encoder(ResultSoulPacket::encode)
                .decoder(ResultSoulPacket::decode)
                .consumerMainThread(ResultSoulPacket::handle)
                .add();

        CHANNEL.messageBuilder(SkillTreePacket.class,nextId())
                .encoder(SkillTreePacket::encode)
                .decoder(SkillTreePacket::decode)
                .consumerMainThread(SkillTreePacket::handle)
                .add();

        CHANNEL.messageBuilder(SkillTreePosPacket.class,nextId())
                .encoder(SkillTreePosPacket::encode)
                .decoder(SkillTreePosPacket::decode)
                .consumerMainThread(SkillTreePosPacket::handle)
                .add();

        CHANNEL.messageBuilder(SkillTreeSyncPacket.class,nextId())
                .encoder(SkillTreeSyncPacket::encode)
                .decoder(SkillTreeSyncPacket::decode)
                .consumerMainThread(SkillTreeSyncPacket::handle)
                .add();

        CHANNEL.messageBuilder(StatusPuzzleItemSyncPacket.class,nextId())
                .encoder(StatusPuzzleItemSyncPacket::encode)
                .decoder(StatusPuzzleItemSyncPacket::decode)
                .consumerMainThread(StatusPuzzleItemSyncPacket::handle)
                .add();

        CHANNEL.messageBuilder(BagLootTableSyncPacket.class,nextId())
                .encoder(BagLootTableSyncPacket::encode)
                .decoder(BagLootTableSyncPacket::decode)
                .consumerMainThread(BagLootTableSyncPacket::handle)
                .add();
    }
}
