package com.Nuaah.NRogueLikeSurvivalBox.regi.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillNodeData {
    public final String name;
    public final float value;
    public final ItemStack icon;
    public final int x, y; // キャンバス上の絶対座標
    public boolean unlocked = false;
    public List<SkillNodeData> children = new ArrayList<>();
    public int requestSoul = 0;
    public boolean defaultNode = false; //最初から使える
    public boolean enabled = false; //有効化

    public SkillNodeData(String name, Item item,float value, int x, int y,int soul) {
        this.name = name;
        this.value = value;
        this.icon = new ItemStack(item);
        this.x = x;
        this.y = y;
        this.requestSoul = soul;
    }

    public SkillNodeData(String name, Item item,float value, int x, int y,int soul,boolean defaultNode) {
        this.name = name;
        this.value = value;
        this.icon = new ItemStack(item);
        this.x = x;
        this.y = y;
        this.requestSoul = soul;
        this.defaultNode = defaultNode;
    }

    public void addChild(SkillNodeData child) {
        this.children.add(child);
    }
}
