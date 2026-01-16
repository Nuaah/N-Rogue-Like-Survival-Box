package com.Nuaah.NRogueLikeSurvivalBox.regi;

import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoul;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoulData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoulProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DeathScreenHandler {

    public static int lastLifetimeSoul = 0;

    // 画面が描画された後に実行される処理
    @SubscribeEvent
    public void onScreenRender(ScreenEvent.Render.Post event) {
        // 死亡画面（DeathScreen）が表示されている時だけ実行
        if (event.getScreen() instanceof DeathScreen) {
            GuiGraphics graphics = event.getGuiGraphics();
            Font font = Minecraft.getInstance().font;

            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            String text = String.valueOf(lastLifetimeSoul);

            // 画面の中央（横方向）を計算
            int x = event.getScreen().width / 2 - font.width(text) / 2;
            int y = 40; // 画面上部からの距離（数字が小さいほど上）

            // 文字を描画
            graphics.drawString(font, text, x, y, 0xFFFFFFFF);

            Component resultSoulTitle = Component.translatable("death_screen.result_soul").withStyle(ChatFormatting.BOLD);
            int textWidth = font.width(resultSoulTitle);  // 文字列のピクセル幅を取得
            x = (event.getScreen().width / 2) - (textWidth / 2);  // 中央位置を計算

            graphics.drawString(font, resultSoulTitle, x, y - 32, 0xc157FFFF);
            graphics.renderItem(new ItemStack(NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get()), event.getScreen().width/2-8, y - 20);
        }
    }

    public static void setResultSoul(int result){
        lastLifetimeSoul = result;
    }
}
