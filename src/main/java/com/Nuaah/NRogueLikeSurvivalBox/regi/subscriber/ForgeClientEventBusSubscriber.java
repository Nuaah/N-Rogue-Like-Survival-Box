package com.Nuaah.NRogueLikeSurvivalBox.regi.subscriber;

import com.Nuaah.NRogueLikeSurvivalBox.gui.screen.SkillTreeScreen;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.PlayerStatusPuzzleProvider;
import com.Nuaah.NRogueLikeSurvivalBox.regi.loader.BagLootTableLoader;
import com.Nuaah.NRogueLikeSurvivalBox.regi.net.DistanceDifficultyPacket;
import com.Nuaah.NRogueLikeSurvivalBox.regi.net.NetworkHandler;
import com.Nuaah.NRogueLikeSurvivalBox.regi.net.StatusPuzzleOpenPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Mod.EventBusSubscriber(modid = NRogueLikeSurvivalBox.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@SuppressWarnings("removal")
public class ForgeClientEventBusSubscriber {
    @SubscribeEvent
    public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
        Entity entity = event.getEntity();

        // 特定の条件（例：JSONデータが存在する敵など）の時だけ描画
        // ここでは例として、カスタムデータを持っているか判定
        renderCustomNameplate(event);
    }

    // ネームプレート
    private static void renderCustomNameplate(RenderLivingEvent.Post<?, ?> event) {
        PoseStack poseStack = event.getPoseStack();
        Entity entity = event.getEntity();

        Player localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;

        // --- 距離の制限 (例: 16ブロック以内) ---
        double maxDistance = 64.0;
        if (entity.distanceToSqr(localPlayer) > maxDistance * maxDistance) return;

        String entityId = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).getPath().toString();
        if(BagLootTableLoader.BAG_LOOT_TABLE_DATA.get(entityId) == null) return; //データなし

        // 1. 描画位置の調整（頭の上）
        float height = entity.getBbHeight() + 0.5f;
        poseStack.pushPose();
        poseStack.translate(0, height, 0);

        // 2. プレイヤーの方向を向かせる（ビルボード処理）
        Quaternionf cameraOrientation = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();
        poseStack.mulPose(cameraOrientation);

        // 3. サイズ調整（マイクラの文字はデフォルトで巨大なので縮小する）
        poseStack.scale(-0.025f, -0.025f, 0.025f);

        // 4. 描画
        int backgroundOpacity = (int)(Minecraft.getInstance().options.getBackgroundOpacity(0.25F) * 255.0F) << 24;
        String id = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString();
        Component component = Component.translatable("entity." + id.replace(":","."));

        Font font = Minecraft.getInstance().font;
        var visualOrder = component.getVisualOrderText();
        float x = (float)(-font.width(visualOrder) / 2);
        Matrix4f matrix4f = poseStack.last().pose();

        // 背景の描画（半透明の黒など）
        font.drawInBatch(component, x, 0, 553648127, false, matrix4f, event.getMultiBufferSource(), Font.DisplayMode.NORMAL, backgroundOpacity, event.getPackedLight());
        // 文字の描画
        font.drawInBatch(component, x, 0, -1, false, matrix4f, event.getMultiBufferSource(), Font.DisplayMode.NORMAL, 0, event.getPackedLight());

        //レベル
        CompoundTag data = event.getEntity().getPersistentData();

        Component levelComponent = Component.literal("LV." + data.getInt("Level"));
        visualOrder = levelComponent.getVisualOrderText();
        x = (float)(-font.width(visualOrder) / 2);

        font.drawInBatch(levelComponent, x, 10, 553648127, false, matrix4f, event.getMultiBufferSource(), Font.DisplayMode.SEE_THROUGH, backgroundOpacity, event.getPackedLight());
        font.drawInBatch(levelComponent, x, 10, -1, false, matrix4f, event.getMultiBufferSource(), Font.DisplayMode.NORMAL, 0, event.getPackedLight());

        poseStack.popPose();
    }

    //メニュータブの実装

    private static final ResourceLocation TABS_TEXTURE =
            new ResourceLocation("minecraft", "textures/gui/advancements/tabs.png");
    private static final ResourceLocation SKILLTREE_BUTTON_TEXTURE =
            new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "textures/gui/skill_tree_button.png");

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof InventoryScreen screen) {
            GuiGraphics graphics = event.getGuiGraphics();
            int x = screen.getGuiLeft();
            int y = screen.getGuiTop();

            // 1. 未選択状態のタブをインベントリの背後に描画
            // (x, y座標は調整してください。-28はタブが上に飛び出す標準的な高さです)
            graphics.blit(TABS_TEXTURE, x + 118, y - 28, 28, 32, 28, 32);
            graphics.blit(TABS_TEXTURE, x + 148, y - 28, 56, 0, 28, 32);


            // 2. タブの中にアイコン（自作アイテムなど）を描画
            graphics.renderItem(new ItemStack(Blocks.CRAFTING_TABLE), x + 124, y - 18);
            graphics.renderItem(new ItemStack(Items.BOOK), x + 154, y - 18);

            // マウス位置取得
            double mouseX = event.getMouseX();
            double mouseY = event.getMouseY();

            boolean isHovering = mouseX >= x + 128 && mouseX <= x + 148 &&
                    mouseY >= y + 61 && mouseY <= y + 79;

            if (isHovering) {
                graphics.blit(SKILLTREE_BUTTON_TEXTURE, x + 128, y + 61, 0, 18, 20, 18, 20, 36);
            } else {
                graphics.blit(SKILLTREE_BUTTON_TEXTURE, x + 128, y + 61, 0, 0, 20, 18, 20, 36);
            }
        }
    }

    @SubscribeEvent
    public static void onMouseClicked(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getScreen() instanceof InventoryScreen screen) {

            if(event.getButton() != 0) return;;

            double mouseX = event.getMouseX();
            double mouseY = event.getMouseY();
            int x = screen.getGuiLeft();
            int y = screen.getGuiTop();

            if (mouseX >= x + 148 && mouseX <= x + 148 + 28 && mouseY >= y - 28 && mouseY <= y) {
                NetworkHandler.CHANNEL.sendToServer(new StatusPuzzleOpenPacket());
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }

            if (mouseX >= x + 128 && mouseX <= x + 148 && mouseY >= y + 61 && mouseY <= y + 79) {
                Minecraft.getInstance().setScreen(new SkillTreeScreen());
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    @SubscribeEvent
    public static void onScreenRenderTooltip(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof InventoryScreen screen) {

            int mouseX = event.getMouseX();
            int mouseY = event.getMouseY();
            int x = screen.getGuiLeft();
            int y = screen.getGuiTop();

            // タブの範囲（例：x+30〜58, y-28〜0）にマウスがあるか
            if (mouseX >= x + 116 && mouseX <= x + 116 + 28 && mouseY >= y - 28 && mouseY <= y) {
                // ツールチップの描画
                event.getGuiGraphics().renderTooltip(
                        Minecraft.getInstance().font,
                        Component.translatable("tooltip.nroguelikesurvivalbox.inventory.tab"),
                        mouseX,
                        mouseY
                );
            }

            if (mouseX >= x + 148 && mouseX <= x + 148 + 28 && mouseY >= y - 28 && mouseY <= y) {
                // ツールチップの描画
                event.getGuiGraphics().renderTooltip(
                        Minecraft.getInstance().font,
                        Component.translatable("tooltip.nroguelikesurvivalbox.status_puzzle.tab"),
                        mouseX,
                        mouseY
                );
            }
        }
    }
}
