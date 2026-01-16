package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxItems;
import com.Nuaah.NRogueLikeSurvivalBox.regi.StatusPuzzleCalculator;
import com.Nuaah.NRogueLikeSurvivalBox.regi.StatusPuzzleManager;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleEffectData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleItemData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.StatusPuzzleItemLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class PlayerStatusPuzzleData implements INBTSerializable<CompoundTag> {

    private static final int COLUMNS = 5;
    private static final int ROWS = 4;

    private static final UUID STP_ATTACK_UUID = UUID.fromString("8b4f9c2d-1e7a-5d3b-9f6e-0a2b4c8d1e5f");
    private static final UUID STP_ARMOR_UUID = UUID.fromString("3d7e1a9f-6b2c-4f8e-0d5a-7e9c2b4f1a8d");
    private static final UUID STP_MOVEMENT_SPEED_UUID = UUID.fromString("7f4e2a1b-3c5d-4e6f-8a9b-0c1d2e3f4a5b");
    private static final UUID STP_ATTACK_KNOCKBACK_UUID = UUID.fromString("5b2a8c6f-e1d9-7f4a-3e8b-2d6c1f9a5e7b");

    //特殊
    private static final UUID STP_MEAT_COUNTUP_UUID = UUID.fromString("c5f2b8e4-9a1d-7c3f-6e0b-2d4a8f1c9e5b");
    private static final UUID STP_ROTTEN_MEAT_COUNTUP_UUID = UUID.fromString("f8a2c7d1-4b9e-3e5f-8a6c-2d7e9f1b4c8a");

    //シナジー
    private static final UUID STP_ENDER_POWER_UUID = UUID.fromString("a7b9c1d2-4e5f-4680-9a1b-3c2d5e7f8901");

    private final Player player;
    public PlayerStatusPuzzleData(Player player){
        this.player = player;
    }

    private final ItemStackHandler inventory = new ItemStackHandler(20) {
        @Override
        protected void onContentsChanged(int slot) {
            updatePlayerStats(player);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    //ステータス計算
    public void updatePlayerStats(Player player) {
        if (player == null || player.level().isClientSide) return;

        //シナジー位置保存
        Map<String, List<Integer>> synergyPos = new HashMap<>();

        for (int i = 0; i < inventory.getSlots(); i++) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(inventory.getStackInSlot(i).getItem());
            StatusPuzzleItemData itemData = StatusPuzzleItemLoader.STP_ITEM_DATA.get(key.getPath());

            if (itemData == null || itemData.slotBonus == null) continue;

            int centerX = -1;
            int centerY = -1;

            //中心取得
            for (StatusPuzzleItemData.slotBonus s : itemData.slotBonus) {
                for (int y = 0; y < s.pattern.length; y++) {
                    for (int x = 0; x < s.pattern[y].length(); x++) {
                        if (s.pattern[y].charAt(x) == '0') {
                            centerX = x;
                            centerY = y;
                            break;
                        }
                    }
                }
            }

            for (StatusPuzzleItemData.slotBonus s : itemData.slotBonus) {
                for (int py = 0; py < s.pattern.length; py++) {
                    for (int px = 0; px < s.pattern[py].length(); px++) {
                        if (s.pattern[py].charAt(px) != ' ') {
                            // 相対座標 (中心からの距離)
                            int dx = px - centerX;
                            int dy = py - centerY;

                            // 絶対座標
                            int absX = i % COLUMNS + dx;
                            int absY = i / COLUMNS + dy;

                            // 3. 5x4のスロット範囲内かチェック (x:0-4, y:0-3)
                            if (absX >= 0 && absX < COLUMNS && absY >= 0 && absY < ROWS) {
                                String num = String.valueOf(s.pattern[py].charAt(px));
                                StatusPuzzleItemData.keyEntry keyEntry = s.key.get(num);
                                if (keyEntry == null)continue;
                                //追加
                                int targetSlotIndex = absY * COLUMNS + absX;

                                // リストに保存
                                synergyPos.computeIfAbsent(keyEntry.name, k -> new ArrayList<>()).add(targetSlotIndex);
                            }
                        }
                    }
                }
            }

            if (centerX == -1) continue;
        }

        List<Float> floatList = calcMultiPosSynergy(synergyPos);

        //攻撃
        float totalBoost = StatusPuzzleCalculator.calculateTotalAttack(inventory,floatList);
        addAttributeModifier(player,Attributes.ATTACK_DAMAGE,"STP Attack Boost",totalBoost,STP_ATTACK_UUID);

        //防御力
        totalBoost = StatusPuzzleCalculator.calculateTotalArmor(inventory,floatList);
        addAttributeModifier(player,Attributes.ARMOR,"STP Armor Boost",totalBoost,STP_ARMOR_UUID);

        //移動速度
        totalBoost = StatusPuzzleCalculator.calculateTotalMoveSpeed(inventory,floatList);
        addAttributeModifier(player,Attributes.MOVEMENT_SPEED,"STP MoveSpeed Boost",totalBoost,STP_MOVEMENT_SPEED_UUID);

        //攻撃のノックバック
        totalBoost = StatusPuzzleCalculator.calculateTotalAttackKnockback(inventory,floatList);
        addAttributeModifier(player,Attributes.ATTACK_KNOCKBACK,"STP Attack Knockback Boost",totalBoost,STP_ATTACK_KNOCKBACK_UUID);

        // スペシャル
        StatusPuzzleManager.removePlayer(player.getUUID()); //リセット
        for (int i = 0; i < inventory.getSlots(); i++) {
            ResourceLocation key = ForgeRegistries.ITEMS.getKey(inventory.getStackInSlot(i).getItem());
            StatusPuzzleItemData itemData = StatusPuzzleItemLoader.STP_ITEM_DATA.get(key.getPath());

            if (itemData == null || itemData.specials == null) continue;

            for (StatusPuzzleItemData.special e : itemData.specials) {
                calculateSpecial(inventory,e.name,player);
            }
        }

        //シナジー
        for (Map.Entry<String, List<Integer>> entry : synergyPos.entrySet()) {

            String bonusType = entry.getKey();
            List<Integer> targetIndices = entry.getValue();

            // 防御力ボーナスの種類を判定
            if ("ender_power".equals(bonusType)) {
                float totalArmorBoost = 0;

                for (Integer index : targetIndices) {
                    if (!inventory.getStackInSlot(index).isEmpty()) {
                        totalArmorBoost += 1F * 0.25F;
                    }
                }

                addAttributeModifier(player,Attributes.ARMOR,"STP Armor Boost",totalArmorBoost,STP_ENDER_POWER_UUID);
            }
        }
    }

    //位置シナジー倍率計算
    private List<Float> calcMultiPosSynergy(Map<String, List<Integer>> synergyPos){
        List<Float> floatList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            floatList.add(1.0F);
        }

        for (Map.Entry<String, List<Integer>> entry : synergyPos.entrySet()) {
            String type = entry.getKey();

            if ("creeper_delete".equals(type)) {
                for (int index : entry.getValue()){
                    floatList.set(index,0F);
                }
            }

            if ("seed_grow".equals(type)) {
                for (int index : entry.getValue()){
                    floatList.set(index,1.2F);
                }
            }
        }

        return floatList;
    }

    //スペシャル
    public static void calculateSpecial(ItemStackHandler inventory,String name,Player player) {
        float totalBoost = 0;
        switch (name){
            case "meat_countup":
                int meats = 0;
                for (int i = 0; i < inventory.getSlots(); i++) {
                    if(inventory.getStackInSlot(i).getItem() == Items.ROTTEN_FLESH) meats ++;
                }

                totalBoost = meats * 0.1F;

                addAttributeModifier(player,Attributes.ATTACK_DAMAGE,"STP Flesh Countup Attack Boost",totalBoost,STP_MEAT_COUNTUP_UUID);
                break;
            case "rotten_meat_countup":
                int meats2 = 0;
                for (int i = 0; i < inventory.getSlots(); i++) {
                    if(inventory.getStackInSlot(i).getItem() == Items.ROTTEN_FLESH) meats2 ++;
                }

                totalBoost = meats2 * 0.2F;

                addAttributeModifier(player,Attributes.ATTACK_DAMAGE,"STP Rotten Flesh Countup Attack Boost",totalBoost,STP_ROTTEN_MEAT_COUNTUP_UUID);

                StatusPuzzleManager.saveEffect(player.getUUID(),name,1F); //変数保存
                break;
            case "poison_fang_attack":
                StatusPuzzleManager.saveEffect(player.getUUID(),name,1F);
                break;
            case "cactus_counter":
                int counterAttack = 0;

                for (int i = 0; i < inventory.getSlots(); i++) {
                    if(inventory.getStackInSlot(i).getItem() == NRogueLikeSurvivalBoxItems.CACTUS_NEEDLE_PROJECTILE.get()) counterAttack++;
                }

                StatusPuzzleManager.saveEffect(player.getUUID(),name,counterAttack);
                break;
        }
    }

    private static void addAttributeModifier(Player player, Attribute att, String desc, float boost, UUID uuid){
        AttributeInstance attr = player.getAttribute(att);
        if (attr != null) {
            attr.removeModifier(uuid);
            if (boost > 0) {
                attr.addTransientModifier(new AttributeModifier(
                    uuid,
                    desc,
                    boost,
                    AttributeModifier.Operation.ADDITION
                ));
            }
        }
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    // NBT（セーブデータ）への保存
    @Override
    public CompoundTag serializeNBT() {
        return inventory.serializeNBT();
    }

    // NBTからの読み込み
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        inventory.deserializeNBT(nbt);
    }
}
