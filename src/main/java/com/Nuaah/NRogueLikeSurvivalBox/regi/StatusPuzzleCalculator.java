package com.Nuaah.NRogueLikeSurvivalBox.regi;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.BagLootTableData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleEffectData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleItemData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.BagLootTableLoader;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.StatusPuzzleItemLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.UUID;

public class StatusPuzzleCalculator {

    private static final int COLUMNS = 5;
    private static final int ROWS = 4;

    //アイテムの効果取得
    public static StatusPuzzleEffectData getEffect(ItemStack stack){
        if (stack.isEmpty()) return new StatusPuzzleEffectData(0, 0,0,0);

        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        StatusPuzzleItemData itemData = StatusPuzzleItemLoader.STP_ITEM_DATA.get(key.getPath());

        if (itemData == null || itemData.effects == null) return new StatusPuzzleEffectData(0, 0,0,0);  //nullはなし

        float attack = 0;
        float armor = 0;
        float movementSpeed = 0;
        float attackKnockback = 0;

        for (StatusPuzzleItemData.effects e : itemData.effects) {

            switch (e.name){
                case "attack":
                    attack = e.value * 0.01F;
                    break;
                case "armor":
                    armor = e.value * 0.25F;
                    break;
                case "movement_speed":
                    movementSpeed = e.value * 0.001F;
                    break;
                case "attack_knockback":
                    attackKnockback = e.value * 0.05F;
                    break;
            }
        }

        return new StatusPuzzleEffectData(attack, armor, movementSpeed,attackKnockback);
    }

    public static float calculateTotalAttack(ItemStackHandler inventory, List<Float> multiList) {
        float totalAttack = 0;

        // 1. 各スロットが周囲から受ける「合計倍率」を先に計算する
        float[] slotMultipliers = new float[20];
        java.util.Arrays.fill(slotMultipliers, 1.0f); // 初期値は1倍

        for (int i = 0; i < COLUMNS * ROWS; i++) {
            if (inventory.getStackInSlot(i).isEmpty()) continue;

            StatusPuzzleEffectData effect = getEffect(inventory.getStackInSlot(i));
            totalAttack += effect.baseAttack * multiList.get(i);
        }

        return totalAttack;
    }

    public static float calculateTotalArmor(ItemStackHandler inventory, List<Float> multiList) {
        float totalArmor = 0;

        float[] slotMultipliers = new float[20];
        java.util.Arrays.fill(slotMultipliers, 1f); // 初期値は1倍

        for (int i = 0; i < COLUMNS * ROWS; i++) {
            if (inventory.getStackInSlot(i).isEmpty()) continue;

            StatusPuzzleEffectData effect = getEffect(inventory.getStackInSlot(i));
            totalArmor += effect.baseArmor * multiList.get(i);
        }

        return totalArmor;
    }

    public static float calculateTotalMoveSpeed(ItemStackHandler inventory, List<Float> multiList) {
        float totalMoveSpeed = 0;

        float[] slotMultipliers = new float[20];
        java.util.Arrays.fill(slotMultipliers, 1f); // 初期値は1倍

        for (int i = 0; i < COLUMNS * ROWS; i++) {
            if (inventory.getStackInSlot(i).isEmpty()) continue;

            StatusPuzzleEffectData effect = getEffect(inventory.getStackInSlot(i));
            totalMoveSpeed += effect.baseMoveSpeed * multiList.get(i);
        }

        return totalMoveSpeed;
    }

    public static float calculateTotalAttackKnockback(ItemStackHandler inventory, List<Float> multiList) {
        float totalAttackKnockback = 0;

        float[] slotMultipliers = new float[20];
        java.util.Arrays.fill(slotMultipliers, 1f); // 初期値は1倍

        for (int i = 0; i < COLUMNS * ROWS; i++) {
            if (inventory.getStackInSlot(i).isEmpty()) continue;

            StatusPuzzleEffectData effect = getEffect(inventory.getStackInSlot(i));
            totalAttackKnockback += effect.attackKnockback * multiList.get(i);
        }

        return totalAttackKnockback;
    }

}
