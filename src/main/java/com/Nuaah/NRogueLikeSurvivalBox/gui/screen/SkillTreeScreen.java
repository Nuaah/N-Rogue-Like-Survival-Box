package com.Nuaah.NRogueLikeSurvivalBox.gui.screen;

import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxItems;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoul;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.LegacySoulProvider;
import com.Nuaah.NRogueLikeSurvivalBox.regi.capability.SkillTreeProvider;
import com.Nuaah.NRogueLikeSurvivalBox.regi.data.SkillNodeData;
import com.Nuaah.NRogueLikeSurvivalBox.regi.net.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
public class SkillTreeScreen extends Screen {
    private double scrollX = 0;
    private double scrollY = 0;
    private final List<SkillNodeData> nodes = new ArrayList<>();

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(NRogueLikeSurvivalBox.MOD_ID, "textures/gui/skill_tree.png");

    private final int imageWidth = 432;
    private final int imageHeight = 256;
    private final int menuWidth = 352;
    private final int menuHeight = 176;

    private float zoom = 1.0f; // 現在のズーム倍率
    private static final float MIN_ZOOM = 0.5f; // 最小縮小率
    private static final float MAX_ZOOM = 2.0f; // 最大拡大率

    public SkillTreeScreen() {
        super(Component.literal("Skill Tree"));
    }

    @Override
    protected void init() {
        super.init();
        // 既存のデータをクリアして、二重登録を防ぐ
        nodes.clear();

        // ここでノードを作成・追加する
        //真ん中
        SkillNodeData LeftSkill = registerNode(null, new SkillNodeData("add_attack", Items.IRON_SWORD, 1F, -52, 0, 20, true));
        SkillNodeData UpSkill = registerNode(null, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 1F, 0, -52, 20, true));
        SkillNodeData DownSkill = registerNode(null, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 1F, 0, 52, 20, true));
        SkillNodeData RightSkill = registerNode(null, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 1F, 52, 0, 20, true));

        SkillNodeData LeftRC = registerNode(null, new SkillNodeData("add_legacy_soul", NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get(), 5F, -208, 52, 100));
        SkillNodeData UpRC = registerNode(null, new SkillNodeData("add_legacy_soul", NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get(), 5F, -52, -208, 100));
        SkillNodeData RightRC = registerNode(null, new SkillNodeData("add_legacy_soul", NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get(), 5F, 208, -52, 100));
        SkillNodeData DownRC = registerNode(null, new SkillNodeData("add_legacy_soul", NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get(), 5F, 52, 208, 100));

        //下
        SkillNodeData Down1_1 = registerNode(DownSkill, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 1F, -52, 52, 50));
        SkillNodeData Down1_2 = registerNode(DownSkill, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 1F, 0, 104, 50));
        SkillNodeData Down2 = registerNode(Down1_1, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 1F, -52, 104, 50));
        Down1_2.addChild(Down2); // 二股の合流
        SkillNodeData Down3_1 = registerNode(Down2, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 1F, -52, 156, 80));
        SkillNodeData Down3_2 = registerNode(Down2, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 1F, -102, 104, 80));
        SkillNodeData DownR1 = registerNode(Down3_2, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, -154, 104, 100));
        SkillNodeData DownR2 = registerNode(DownR1, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 2F, -208, 104, 100));
        DownR2.addChild(LeftRC);
        SkillNodeData DownR1_D = registerNode(Down3_1, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, -52, 208, 100));
        SkillNodeData DownR2_D = registerNode(DownR1_D, new SkillNodeData("add_health", NRogueLikeSurvivalBoxItems.HEART.get(), 2F, 0, 208, 100));
        DownR2_D.addChild(DownRC);

        //左
        SkillNodeData Left1_1 = registerNode(LeftSkill, new SkillNodeData("add_attack", Items.IRON_SWORD, 1F, -104, 0, 50));
        SkillNodeData Left1_2 = registerNode(LeftSkill, new SkillNodeData("add_attack", Items.IRON_SWORD, 1F, -52, -52, 50));
        SkillNodeData Left2 = registerNode(Left1_1, new SkillNodeData("add_attack", Items.IRON_SWORD, 1F, -104, -52, 50));
        Left1_2.addChild(Left2);
        SkillNodeData Left3_1 = registerNode(Left2, new SkillNodeData("add_attack", Items.IRON_SWORD, 1F, -104, -104, 80));
        SkillNodeData Left3_2 = registerNode(Left2, new SkillNodeData("add_attack", Items.IRON_SWORD, 1F, -156, -52, 80));
        SkillNodeData LeftR1_L = registerNode(Left3_2, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, -208, -52, 100));
        SkillNodeData LeftR2_L = registerNode(LeftR1_L, new SkillNodeData("add_attack", Items.IRON_SWORD, 2F, -208, 0, 100));
        LeftR2_L.addChild(LeftRC);
        SkillNodeData LeftR1 = registerNode(Left3_1, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, -104, -156, 100));
        SkillNodeData LeftR2 = registerNode(LeftR1, new SkillNodeData("add_attack", Items.IRON_SWORD, 2F, -104, -208, 100));
        LeftR2.addChild(UpRC);

        //上
        SkillNodeData Up1_1 = registerNode(UpSkill, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 1F, 0, -104, 50));
        SkillNodeData Up1_2 = registerNode(UpSkill, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 1F, 52, -52, 50));
        SkillNodeData Up2 = registerNode(Up1_1, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 1F, 52, -104, 50));
        Up1_2.addChild(Up2);
        SkillNodeData Up3_1 = registerNode(Up2, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 1F, 104, -104, 80));
        SkillNodeData Up3_2 = registerNode(Up2, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 1F, 52, -156, 80));
        SkillNodeData UpR1 = registerNode(Up3_1, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, 156, -104, 100));
        SkillNodeData UpR2 = registerNode(UpR1, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 2F, 208, -104, 100));
        UpR2.addChild(RightRC);
        SkillNodeData UpR1_U = registerNode(Up3_2, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, 52, -208, 100));
        SkillNodeData UpR2_U = registerNode(UpR1_U, new SkillNodeData("add_armor", Items.IRON_CHESTPLATE, 2F, 0, -208, 100));
        UpR2_U.addChild(UpRC);

        //右
        SkillNodeData Right1_1 = registerNode(RightSkill, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 1F, 104, 0, 50));
        SkillNodeData Right1_2 = registerNode(RightSkill, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 1F, 52, 52, 50));
        SkillNodeData Right2 = registerNode(Right1_1, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 1F, 104, 52, 50));
        Right1_2.addChild(Right2);
        SkillNodeData Right3_1 = registerNode(Right2, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 1F, 156, 52, 80));
        SkillNodeData Right3_2 = registerNode(Right2, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 1F, 104, 104, 80));
        SkillNodeData RightR1 = registerNode(Right3_2, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, 104, 156, 100));
        SkillNodeData RightR2 = registerNode(RightR1, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 2F, 104, 208, 100));
        RightR2.addChild(DownRC);
        SkillNodeData RightR1_R = registerNode(Right3_1, new SkillNodeData("add_EXP", Items.EXPERIENCE_BOTTLE, 5F, 208, 52, 100));
        SkillNodeData RightR2_R = registerNode(RightR1_R, new SkillNodeData("add_movement_speed", NRogueLikeSurvivalBoxItems.BOOTS.get(), 2F, 208, 0, 100));
        RightR2_R.addChild(RightRC);

        //解放済み判定
        if (this.minecraft != null && this.minecraft.player != null) {
            for (SkillNodeData node : nodes) {
                Player player = this.minecraft.player;
                player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {

                    List<Vec2> vecList = cap.getSkillPos().get(node.name);
                    if (vecList != null) {
                        for (Vec2 vec : vecList) {
                            if (vec != null && vec.equals(new Vec2(node.x,node.y))) {
                                node.unlocked = true;

                                //繋がってるノード開放
                                for (SkillNodeData linkNode : node.children){
                                    linkNode.enabled = true;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private SkillNodeData registerNode(SkillNodeData parent, SkillNodeData child) {
        if (parent != null) {
            parent.addChild(child);
        }
        nodes.add(child);
        return child;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        int leftPos = (this.width - menuWidth) / 2;
        int topPos = (this.height - menuHeight) / 2;

        //もってる魂
        graphics.renderItem(new ItemStack(NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get()), leftPos, topPos - 16);
        if (this.minecraft != null && this.minecraft.player != null) {
            Player player = this.minecraft.player;
            int legacySoul = player.getCapability(LegacySoulProvider.LEGACY_SOUL)
                    .map(LegacySoul::getSoul) // ここで中身をintに変換
                    .orElse(0);
            graphics.drawString(this.font,String.valueOf(legacySoul), leftPos + 24, topPos - 12, 0xFFFFFF, false);
        }

        //メニュー
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, menuWidth, menuHeight, imageWidth, imageHeight);

        // 2. 「枠の内側」だけを描画するように制限をかける
        // 枠自体の厚みが10ピクセルあると仮定して少し内側を指定
        int padding = 8;
        graphics.enableScissor(
            leftPos + padding,
            topPos + padding,
            leftPos + menuWidth - padding,
            topPos + menuHeight - padding
        );

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.pose().pushPose();

        //画面中央へ移動
        graphics.pose().translate(centerX, centerY, 0);

        //ズーム倍率を適用 (X, Y, Z軸すべてにzoomをかける)
        graphics.pose().scale(zoom, zoom, 1.0f);

        //スクロール位置を適用（ズームした状態での移動にする）
        graphics.pose().translate(scrollX, scrollY, 0);

        // 線の描画
        for (SkillNodeData node : nodes) {
            for (SkillNodeData child : node.children) {
                renderConnection(graphics, node.x + 8, node.y + 8, child.x + 8, child.y + 8,node.unlocked);
            }
        }

        //ノードの描画
        for (SkillNodeData node : nodes) {
            renderNode(graphics, node, mouseX, mouseY);
        }

        graphics.pose().popPose();

        //制限を解除
        graphics.disableScissor();

        for (SkillNodeData node : nodes) {
            skillToolTip(graphics, mouseX, mouseY, node);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void skillToolTip(GuiGraphics graphics, int mouseX, int mouseY,SkillNodeData node){
        //画面中央からの相対距離にする
        double relX = mouseX - (this.width / 2.0);
        double relY = mouseY - (this.height / 2.0);
        //ズーム倍率で割って、スクロール分を引く
        double canvasMouseX = (relX / zoom) - scrollX;
        double canvasMouseY = (relY / zoom) - scrollY;

        if (canvasMouseX >= node.x - 13 && canvasMouseX <= node.x + 13 &&
                canvasMouseY >= node.y - 13 && canvasMouseY <= node.y + 13) {

            List<Component> tooltip = new ArrayList<>();
            tooltip.add(Component.translatable("skill_tree.tooltip.title." + node.name,node.value).withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("skill_tree.tooltip.desc." + node.name,node.value).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("　"));
            tooltip.add(Component.literal("　"));

            tooltip.add(Component.literal("     " + node.requestSoul));
            tooltip.add(Component.literal("　"));
            //ツールチップ全体の幅を計算（最も長い行に合わせる）
            int tooltipWidth = 0;
            for (Component c : tooltip) {
                int w = this.font.width(c);
                if (w > tooltipWidth) tooltipWidth = w;
            }
            tooltipWidth += 10; // 余白分

            //描画開始位置を計算 (Minecraftのロジックをシミュレート)
            int drawX = mouseX + 12;

            //画面右端からはみ出す場合、renderComponentTooltipは左側に表示を反転させる
            if (drawX + tooltipWidth - 10 > this.width) {
                drawX = mouseX - tooltipWidth;
            }

            graphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);

            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 400);

            graphics.renderFakeItem(new ItemStack(NRogueLikeSurvivalBoxItems.LEGACY_SOUL.get()), drawX, mouseY + 25);
            graphics.pose().popPose();
        }
    }

    private void renderConnection(GuiGraphics graphics, int x1, int y1, int x2, int y2,boolean unlocked) {
        //距離と角度の計算
        double dx = x2 - x1;
        double dy = y2 - y1;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float angle = (float) Math.atan2(dy, dx);

        //線の設定
        int color;
        if (unlocked){
            color = 0xDDFFFFFF; // 白
        } else {
            color = 0xAA333333; // 灰
        }
        float thickness = 2.0f; // 太さ

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();

        //座標の移動と回転
        poseStack.translate(x1-8, y1-8, 1); // Z値を少し上げてノードより奥、背景より手前に
        poseStack.mulPose(com.mojang.math.Axis.ZP.rotation(angle));

        //描画 (fillの引数が 最小x, 最小y, 最大x, 最大y になるようにする)
        float minX = 0;
        float maxX = distance;
        float minY = -thickness / 2f;
        float maxY = thickness / 2f;

        graphics.fill((int)minX, (int)minY, (int)maxX, (int)maxY, color);

        poseStack.popPose();
    }

    private void renderNode(GuiGraphics graphics, SkillNodeData node, int mouseX, int mouseY) {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();

        //ノードの座標(x, y)に移動し、Z軸を手前(5)に設定
        poseStack.translate(node.x, node.y, 5);

        //背景枠を描画
        //translateで既にnode.xの位置にいるので、ここでは「中心を合わせるための微調整」だけを行う
        if (node.unlocked){ //取得済み
            graphics.blit(TEXTURE, -13, -13, 26, 176, 26, 26, imageWidth, imageHeight);
        } else if (node.enabled || node.defaultNode){
            graphics.blit(TEXTURE, -13, -13, 52, 176, 26, 26, imageWidth, imageHeight);
        } else {
            graphics.blit(TEXTURE, -13, -13, 0, 176, 26, 26, imageWidth, imageHeight);
        }

        poseStack.translate(0, 0, 1);
        graphics.renderFakeItem(node.icon, -8, -8);

        poseStack.popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //ツールチップ判定と同じ逆算を行う
        double relX = mouseX - (this.width / 2.0);
        double relY = mouseY - (this.height / 2.0);
        double canvasMouseX = (relX / zoom) - scrollX;
        double canvasMouseY = (relY / zoom) - scrollY;

        for (SkillNodeData node : nodes) {
            if (canvasMouseX >= node.x - 13 && canvasMouseX <= node.x + 13 &&
                    canvasMouseY >= node.y - 13 && canvasMouseY <= node.y + 13) {

                if ((!node.unlocked && node.enabled) || node.defaultNode) {
                    LocalPlayer player =  Minecraft.getInstance().player;

                    int legacySoul = player.getCapability(LegacySoulProvider.LEGACY_SOUL)
                            .map(LegacySoul::getSoul) // ここで中身をintに変換
                            .orElse(0);

                    if(node.requestSoul <= legacySoul) {
                        //魂減る
                        player.getCapability(LegacySoulProvider.LEGACY_SOUL).ifPresent(cap -> {

                            cap.setSoul(legacySoul - node.requestSoul);
                            for (SkillNodeData linkNode : node.children){ //繋がってるノード二使用許可
                                linkNode.enabled = true;
                            }
                            NetworkHandler.CHANNEL.sendToServer(new SkillTreePacket(node.name,node.value));
                            NetworkHandler.CHANNEL.sendToServer(new SkillTreePosPacket(node.name,node.x,node.y));
                        });

                        //魂同期
                        NetworkHandler.CHANNEL.sendToServer(new LegacySoulPacket(legacySoul - node.requestSoul));

                        player.getCapability(SkillTreeProvider.SKILL_TREE).ifPresent(cap -> {
                            cap.setSkill(node.name,node.value);
                            cap.setSkillPos(node.name,node.x,node.y);
                        });

                        node.unlocked = true;
                        this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0F));
                    }

                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {

        if (delta > 0) {
            zoom = Math.min(MAX_ZOOM, zoom + 0.1f);
        } else if (delta < 0) {
            zoom = Math.max(MIN_ZOOM, zoom - 0.1f);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        this.scrollX += dragX;
        this.scrollY += dragY;
        return true;
    }
}
