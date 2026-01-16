package com.Nuaah.NRogueLikeSurvivalBox.regi.subscriber;

import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxItems;
import com.Nuaah.NRogueLikeSurvivalBox.regi.SkillAttributeModifier;
import com.Nuaah.NRogueLikeSurvivalBox.regi.StatusPuzzleManager;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.*;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.BagLootTableData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.LootBagData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleItemData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.BagLootTableLoader;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.BagLootTableManager;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.StatusPuzzleItemLoader;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.StatusPuzzleItemManager;
import com.Nuaah.NRogueLikeSurvivalBox.regi.net.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.*;

@Mod.EventBusSubscriber(modid = NRogueLikeSurvivalBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
@SuppressWarnings("removal")
public class ForgeEventBusSubscriber {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {

        if (event.getEntity().level().isClientSide) return;

        LivingEntity entity = event.getEntity();
        // エンティティタイプ
        EntityType<?> type = entity.getType();

        ResourceKey<EntityType<?>> key = BuiltInRegistries.ENTITY_TYPE.getResourceKey(type).orElse(null);
        ResourceLocation id = key.location();
        BagLootTableData bagData = BagLootTableLoader.BAG_LOOT_TABLE_DATA.get(id.getPath());

        if (bagData == null) return;

        ItemStack dropStack = new ItemStack(NRogueLikeSurvivalBoxItems.BROWN_BAG.get());
        int level = entity.getPersistentData().getInt("Level");

        List<LootBagData> dropList = new ArrayList<>();

        //アイテムの設定
        if (bagData.pools != null) {
            for (BagLootTableData.pools c : bagData.pools) {

                if (c.level > level) continue;  //指定レベル

                for (BagLootTableData.entries entries : c.entries) {
                    Random random = new Random();
                    Random dropRandom = new Random();

                    if (entries.weight >= dropRandom.nextInt(0, 100)) {
                        dropList.add(new LootBagData(entries.id, random.nextInt(entries.min, entries.max + 1))); //アイテム設定
                    }
                }
            }
        }

        if (dropList.isEmpty()) return; //空だったら出さない
        System.out.println("DROP");
        // 2. NBT構造を構築
        CompoundTag rootTag = dropStack.getOrCreateTag();
        ListTag itemListTag = new ListTag();

        for (LootBagData data : dropList) {
            CompoundTag itemEntry = new CompoundTag();
            itemEntry.putString("Name", data.itemName);
            itemEntry.putInt("Count", data.count);
            itemListTag.add(itemEntry); // リストに追加
        }

        // 3. ルートのタグにリストを保存
        rootTag.put("LootTable", itemListTag);
        rootTag.putString("EntityNameID",id.toString());

        // 3. ドロップリストに追加
        BlockPos pos = event.getEntity().blockPosition();
        ItemEntity itemEntity = new ItemEntity(
                event.getEntity().level(),
                pos.getX(), pos.getY(), pos.getZ(),
                dropStack
        );

        event.getDrops().add(itemEntity);

    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        // ここでインスタンスを登録します
        event.addListener(BagLootTableLoader.INSTANCE);
        event.addListener(StatusPuzzleItemLoader.INSTANCE);
    }

    //Capabilityの登録

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(PlayerStatusPuzzleData.class);
    }

    // 2. プレイヤーが生成された時にデータをくっつける
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!event.getObject().getCapability(PlayerStatusPuzzleProvider.AUGMENT_INVENTORY).isPresent()) {
                event.addCapability(new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "status_puzzle"), new PlayerStatusPuzzleProvider(player));
            }
            if (!event.getObject().getCapability(LegacySoulProvider.LEGACY_SOUL).isPresent()) {
                event.addCapability(new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "legacy_soul"), new LegacySoulProvider());
            }
            if (!event.getObject().getCapability(SkillTreeProvider.SKILL_TREE).isPresent()) {
                event.addCapability(new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "skill_tree"), new SkillTreeProvider());
            }
            if (!event.getObject().getCapability(PlayerHealthSyncProvider.PLAYER_HEALTH).isPresent()) {
                event.addCapability(new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "player_health"), new PlayerHealthSyncProvider());
            }
        }
    }

    //魂の軌跡同期
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(soul -> {
                NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new LegacySoulPacket(soul.getSoul()));
            });
            player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SkillTreeSyncPacket(cap.serializeNBT()));

                //スキル属性付与
                Map<String, Float> skill = cap.getSkill();
                for (Map.Entry<String, Float> entry : skill.entrySet()) {
                    String name = entry.getKey();
                    Float value = entry.getValue();

                    SkillAttributeModifier.checkSkill(player,name,value);
                }
            });

            //増加分の体力設定
            player.getCapability(PlayerHealthSyncProvider.PLAYER_HEALTH).ifPresent(cap -> {
                player.setHealth(cap.getHealth());
            });

            Map<String, StatusPuzzleItemData> dataToSend = StatusPuzzleItemManager.getServerData();

            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                    new StatusPuzzleItemSyncPacket(dataToSend));

            System.out.println("PlayerEventHandler: Sending " + dataToSend.size() + " entries to player.");

            Map<String, BagLootTableData> dataToBagLootTable = BagLootTableManager.getServerData();

            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                    new BagLootTableSyncPacket(dataToBagLootTable));

            System.out.println("PlayerEventHandler: Sending " + dataToSend.size() + " entries to player.");

            player.getCapability(PlayerStatusPuzzleProvider.AUGMENT_INVENTORY).ifPresent(cap -> {
                // ここで計算と反映を行うメソッドを呼ぶ
                // Dataクラス内に updatePlayerStats(player) を作っている場合はそれを呼ぶ
                cap.updatePlayerStats(player);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerHealthSyncProvider.PLAYER_HEALTH).ifPresent(cap -> {
                cap.setHealth(player.getHealth());
            });

            StatusPuzzleManager.removePlayer(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player newPlayer = event.getEntity();
            Player oldPlayer = event.getOriginal();

            oldPlayer.reviveCaps();

            oldPlayer.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(oldStore -> {
                newPlayer.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(newStore -> {
                    int resultSoul = oldStore.getKillCount() * oldStore.getKillLevels();
                    newStore.setSoul(oldStore.getSoul());
                    newStore.addSoul(resultSoul);
                });
            });

            oldPlayer.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(oldStore -> {
                newPlayer.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(newStore -> {

                    Map<String, Float> skill = oldStore.getSkill();
                    Map<String, List<Vec2>> skillPos = oldStore.getSkillPos();

                    for (Map.Entry<String, Float> entry : skill.entrySet()) {
                        String name = entry.getKey();
                        Float value = entry.getValue();

                        newStore.setSkill(name,value);
                    }

                    for (Map.Entry<String, List<Vec2>> entry : skillPos.entrySet()) {
                        String name = entry.getKey();
                        for (Vec2 pos : entry.getValue()){
                            newStore.setSkillPos(name,(int)pos.x,(int)pos.y);
                        }
                    }
                });
            });

            // 処理が終わったら再び無効化しておく（メモリリーク防止）
            oldPlayer.invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        // 死亡したのがプレイヤーであること、かつクライアント側の処理であることを確認
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(cap -> {

                player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(tree -> {
                    int resultSoul = cap.getKillLevels();

                    if (tree.getSkill().get("add_legacy_soul") != null){
                        resultSoul = (int)(resultSoul * (1f + tree.getSkill().get("add_legacy_soul") * 0.01F));
                    }

                    cap.setLifetimeSoul(resultSoul);

                    NetworkHandler.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> player),
                            new ResultSoulPacket(resultSoul)
                    );
                });

//                player.sendSystemMessage(Component.literal("魂の軌跡を得た: " + resultSoul));
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(cap -> {
                NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new LegacySoulPacket(cap.getSoul()));
            });

            player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                Map<String, Float> skill = cap.getSkill();
                Map<String, List<Vec2>> skillPos = cap.getSkillPos();

                for (Map.Entry<String, Float> entry : skill.entrySet()) {
                    String name = entry.getKey();
                    Float value = entry.getValue();
                    NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SkillTreePacket(name, value));

                    SkillAttributeModifier.checkSkill(player,name,value);  //スキル属性付与
                }

                for (Map.Entry<String, List<Vec2>> entry : skillPos.entrySet()) {
                    System.out.println("DEATH2");
                    String name = entry.getKey();
                    for (Vec2 pos : entry.getValue()){
                        NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SkillTreePosPacket(name,(int)pos.x,(int)pos.y));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onEnemyKilled(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof ServerPlayer player) {

            // 対象が敵（モンスター）かチェック（動物も含むなら LivingEntity でOK）
            if (event.getEntity() instanceof Monster entity) {
                // 1. Capabilityを取得して加算
                EntityType<?> type = entity.getType();

                ResourceKey<EntityType<?>> key = BuiltInRegistries.ENTITY_TYPE.getResourceKey(type).orElse(null);
                ResourceLocation id = key.location();
                BagLootTableData bagData = BagLootTableLoader.BAG_LOOT_TABLE_DATA.get(id.getPath());

                if (bagData == null) return;

                player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(cap -> {
                    cap.addKillCount(1);
                    cap.addKillLevels(event.getEntity().getPersistentData().getInt("Level"));

                    NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new KillCountPacket(cap.getKillCount(),cap.getKillLevels()));
                });
            }
        }
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player && event.getEntity() != null) {
            LivingEntity target = event.getEntity();
            UUID uuid = player.getUUID();
            if(StatusPuzzleManager.getEffectValue(uuid,"rotten_meat_countup") > 0){
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 0));
            }
            if(StatusPuzzleManager.getEffectValue(uuid,"poison_fang_attack") > 0){
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0));
            }
        }
    }

    @SubscribeEvent
    public static void onExpDrop(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() instanceof ServerPlayer player) {

            player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                if (cap.getSkill().get("add_EXP") != null) {
                    float multi = cap.getSkill().get("add_EXP");
                    int originalExp = event.getDroppedExperience();
                    int bonusExp = (int) (originalExp * (1F + multi * 0.01F));

                    event.setDroppedExperience(bonusExp);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        StatusPuzzleItemManager.loadDataOnServer();
        BagLootTableManager.loadDataOnServer();
    }

    @SubscribeEvent
    public static void onFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {

        if (!(event.getEntity() instanceof Monster monster)) return;

        // 1. スポーン地点からの距離を計算
        LevelData spawnData = event.getLevel().getLevelData();
        double distance = monster.position().distanceTo(new Vec3(spawnData.getXSpawn(), spawnData.getYSpawn(), spawnData.getZSpawn()));

        int tier = (int) (distance / 90);

        //レベルの計算
        RandomSource random = event.getEntity().getRandom();
        int entityLevel = random.nextInt(tier * 2, 3 + tier * 2 + 1); // ここで計算

        monster.getPersistentData().putInt("DifficultyTier", tier);
        monster.getPersistentData().putInt("Level", entityLevel);

        NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> monster),
                new DistanceDifficultyPacket(monster.getId(), tier,entityLevel));

        // 3. 最大体力の変更
        AttributeInstance healthAttr = monster.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttr != null) {
            double newHealth = healthAttr.getBaseValue() * (entityLevel * 0.01 + 1);
//            double newHealth = 10000;
            healthAttr.setBaseValue(newHealth);
            monster.setHealth((float) newHealth); // 現在の体力もセット
        }

        // 4. 攻撃力の変更
        AttributeInstance attackAttr = monster.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackAttr != null) {
            double oldValue = attackAttr.getBaseValue();
            double multiplier = (Math.floor(entityLevel / 5.0) * 0.80 + 1);
            double newValue = oldValue * multiplier;

            attackAttr.setBaseValue(newValue);
        }
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        //距離でのレベルの同期
        if (event.getTarget() instanceof Monster monster) {
            CompoundTag data = monster.getPersistentData();

            // 保存済みの Tier と Level を取得
            if (data.contains("DifficultyTier") && data.contains("Level")) {
                int tier = data.getInt("DifficultyTier");
                int level = data.getInt("Level");

                // 追跡を開始したプレイヤー（描画範囲に入ったプレイヤー）だけにパケットを送る
                NetworkHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()),
                        new DistanceDifficultyPacket(monster.getId(), tier,level) // 3つの引数を渡す
                );
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Monster monster) {
            System.out.println("DAMAGE");
            UUID uuid = player.getUUID();
            if(StatusPuzzleManager.getEffectValue(uuid,"cactus_counter") > 0) {

                float damage = StatusPuzzleManager.getEffectValue(uuid,"cactus_counter");
                monster.hurt(monster.damageSources().thorns(monster), damage);
                System.out.println(damage);
            }
        }
    }
}
