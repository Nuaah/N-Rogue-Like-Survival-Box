package com.Nuaah.NRogueLikeSurvivalBox.regi.subscriber;

import com.Nuaah.NRogueLikeSurvivalBox.regi.data.BagLootTableData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.StatusPuzzleItemData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.BagLootTableLoader;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.StatusPuzzleItemLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TooltipSubscriber {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        String id = stack.getItem().toString();
        StatusPuzzleItemData itemData = StatusPuzzleItemLoader.STP_ITEM_DATA.get(id);

        if (itemData == null) return;

        if (itemData.effects != null){
            for (StatusPuzzleItemData.effects e : itemData.effects) {
                Component valueLine = Component.empty();

                if (e.value > 0){
                    valueLine = Component.literal(" : +" + e.value);
                } else if(e.value < 0) {
                    valueLine = Component.literal(" : " + e.value);
                }

                tooltip.add(Component.translatable("tooltip.stp." + e.name).withStyle(ChatFormatting.YELLOW).append(valueLine));
            }
        }

        if (itemData.specials != null) {
            for (StatusPuzzleItemData.special e : itemData.specials) {
                tooltip.add(Component.translatable("tooltip.stp.special." + e.name).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
                if(Float.isNaN(e.value)){
                    tooltip.add(Component.translatable("tooltip.stp.special.desc." + e.name).withStyle(ChatFormatting.LIGHT_PURPLE));
                } else {
                    tooltip.add(Component.translatable("tooltip.stp.special.desc." + e.name , e.value).withStyle(ChatFormatting.LIGHT_PURPLE));
                }
            }
        }

        //シナジー
        if (itemData.slotBonus != null) {
            tooltip.add(Component.translatable("tooltip.stp.slot_bonus.title").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
            for (StatusPuzzleItemData.slotBonus c : itemData.slotBonus) {
                for (String pattern : c.pattern) { // 1行ずつ取り出す (" + ", "+0+", " + ")
                    MutableComponent patternLine = Component.empty();

                    for (int i = 0; i < pattern.length(); i++) {
                        char patternChar = pattern.charAt(i);

                        if (patternChar == '0') {
                            patternLine.append(Component.literal("◎").withStyle(ChatFormatting.GOLD));
                        } else if (patternChar == ' ') {
                            patternLine.append(Component.literal("  "));
                        } else {
                            patternLine.append(Component.literal("◎").withStyle(ChatFormatting.GRAY));
                        }
                    }
                    tooltip.add(patternLine);
                }

                for (Map.Entry<String,StatusPuzzleItemData.keyEntry> entry : c.key.entrySet()){
                    tooltip.add(Component.translatable("tooltip.stp.slot_bonus." + entry.getValue().name).withStyle(ChatFormatting.RED));
                }
            }
        }
    }
}
