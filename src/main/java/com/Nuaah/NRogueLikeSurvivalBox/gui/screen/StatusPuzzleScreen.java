package com.Nuaah.NRogueLikeSurvivalBox.gui.screen;

import com.Nuaah.NRogueLikeSurvivalBox.gui.container.StatusPuzzleManu;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("removal")
public class StatusPuzzleScreen extends AbstractContainerScreen<StatusPuzzleManu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "textures/gui/status_puzzle.png");

    private static final ResourceLocation TABS_TEXTURE =
            new ResourceLocation("minecraft", "textures/gui/advancements/tabs.png");

    public StatusPuzzleScreen(StatusPuzzleManu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
//        this.imageHeight = 204; // 通常より少し縦長にする（5x5があるため）

        this.inventoryLabelY = this.imageHeight - 10000;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        graphics.blit(TABS_TEXTURE,x + 118,y - 28,28, 0, 28, 32);
        graphics.blit(TABS_TEXTURE,x + 148,y - 28,56, 32,28,32);
        graphics.renderItem(new ItemStack(Blocks.CRAFTING_TABLE), x + 124, y - 18);
        graphics.renderItem(new ItemStack(Items.BOOK), x + 154, y - 18);
        graphics.renderItem(new ItemStack(NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get()), x, y - 18);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        graphics.drawString(this.font,String.valueOf(menu.getLegacySoul()), x + 24, y - 14, 0xFFFFFF, false);
    }

        @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderTooltip(graphics, mouseX, mouseY);

        int x = this.leftPos;
        int y = this.topPos;

        // 【判定範囲の設定】
        // 例：インベントリ画面と同じ位置（左から30〜58ピクセル、上側に28ピクセル）
        if (mouseX >= x + 116 && mouseX <= x + 116 + 28 && mouseY >= y - 28 && mouseY <= y) {
            graphics.renderTooltip(
                    Minecraft.getInstance().font,
                    Component.translatable("tooltip.nroguelikesurvivalbox.inventory.tab"),
                    mouseX,
                    mouseY
            );
        }

        if (mouseX >= x + 148 && mouseX <= x + 148 + 28 && mouseY >= y - 28 && mouseY <= y) {
            // ツールチップの描画
            graphics.renderTooltip(
                    Minecraft.getInstance().font,
                    Component.translatable("tooltip.nroguelikesurvivalbox.status_puzzle.tab"),
                    mouseX,
                    mouseY
            );
        }

        if (mouseX >= x && mouseX <= x + 16 && mouseY >= y - 16 && mouseY <= y) {
            // ツールチップの描画
            graphics.renderTooltip(
                Minecraft.getInstance().font,
                List.of(
                    // 1行目：タイトル
                    Component.translatable("tooltip.nroguelikesurvivalbox.legacy_soul.title")
                        .withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD),
                    // 2行目：説明
                    Component.translatable("tooltip.nroguelikesurvivalbox.legacy_soul.desc")
                        .withStyle(ChatFormatting.GRAY)
                ),
                Optional.empty(), // ツールチップのアイコンなど（通常はemptyでOK）
                mouseX,
                mouseY
            );
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // button == 0 は左クリックを指します
        if (button == 0) {
            int x = this.leftPos;
            int y = this.topPos;

            // 【判定範囲の設定】
            // 例：インベントリ画面と同じ位置（左から30〜58ピクセル、上側に28ピクセル）
            if (mouseX >= x + 116 && mouseX <= x + 116 + 28 && mouseY >= y - 28 && mouseY <= y) {

                // クリック音を鳴らす（バニラ基準）
                Minecraft.getInstance().getSoundManager().play(
                        SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
                );

                // バニラのインベントリ画面へ切り替え
                Minecraft.getInstance().setScreen(new InventoryScreen(this.minecraft.player));
                return true; // イベントをここで消費
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
