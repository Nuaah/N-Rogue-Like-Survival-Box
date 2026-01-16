package com.Nuaah.NRogueLikeSurvivalBox.regi.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillTreeData implements SkillTree{
    private Map<String,Float> skill = new HashMap<>();
    private Map<String, List<Vec2>> skillPos = new HashMap<>();

    public void setSkill(String skillName, float value) {
        this.skill.put(skillName, value);
    }

    @Override
    public void addSkill(String skillName, float value) {
        if (skill.containsKey(skillName)) {
            skill.put(skillName, skill.get(skillName) + value);
        } else {
            skill.put(skillName, value);
        }
        System.out.println(skill);
    }

    public void setSkillPos(String skillName, int x,int y) {
        Vec2 pos = new Vec2(x,y);
        if (skillPos.containsKey(skillName)) {
            // 同じスキル名がある場合：Listに追加
            skillPos.get(skillName).add(pos);
        } else {
            // スキル名がない場合：新しいListを作成して追加
            List<Vec2> posList = new ArrayList<>();
            posList.add(pos);
            skillPos.put(skillName, posList);
        }
        System.out.println(skillPos);
    }

    @Override
    public Map<String,Float> getSkill() {
        return skill;
    }

    @Override
    public Map<String, List<Vec2>> getSkillPos() {
        return skillPos;
    }

    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        // --- skill の保存 ---
        ListTag skillList = new ListTag();
        for (Map.Entry<String, Float> entry : skill.entrySet()) {
            CompoundTag tag = new CompoundTag();
            tag.putString("Name", entry.getKey());
            tag.putFloat("Value", entry.getValue());
            skillList.add(tag);
        }
        nbt.put("Skill", skillList);

        // --- skillPos の保存 (修正版) ---
        ListTag skillPosOuterList = new ListTag(); // 全体のリスト
        for (Map.Entry<String, List<Vec2>> entry : skillPos.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putString("Name", entry.getKey());

            ListTag posList = new ListTag(); // Vec2のリスト
            for (Vec2 pos : entry.getValue()) {
                CompoundTag vTag = new CompoundTag();
                vTag.putFloat("x", pos.x);
                vTag.putFloat("y", pos.y);
                posList.add(vTag);
            }
            entryTag.put("Positions", posList);
            skillPosOuterList.add(entryTag);
        }
        nbt.put("SkillPos", skillPosOuterList);

        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        skill.clear();
        ListTag skillList = nbt.getList("Skill", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < skillList.size(); i++) {
            CompoundTag tag = skillList.getCompound(i);
            skill.put(tag.getString("Name"), tag.getFloat("Value"));
        }

        // skillPos Mapを読み込み
//        skillPos.clear();
//        ListTag skillPosList = nbt.getList("SkillPos", CompoundTag.TAG_COMPOUND);
//        for (int i = 0; i < skillPosList.size(); i++) {
//            CompoundTag tag = skillPosList.getCompound(i);
//            String name = tag.getString("Name");
//            Vec2 pos = new Vec2(tag.getFloat("x"), tag.getFloat("y"));
//
//            if (skillPos.containsKey(name)) {
//                // 同じスキル名がある場合：Listに追加
//                skillPos.get(name).add(pos);
//            } else {
//                // スキル名がない場合：新しいListを作成して追加
//                List<Vec2> posList = new ArrayList<>();
//                posList.add(pos);
//                skillPos.put(name, posList);
//            }
//        }

        skillPos.clear();
        ListTag skillPosOuterList = nbt.getList("SkillPos", CompoundTag.TAG_COMPOUND);
        for (int i = 0; i < skillPosOuterList.size(); i++) {
            CompoundTag entryTag = skillPosOuterList.getCompound(i);
            String name = entryTag.getString("Name");

            List<Vec2> posList = new ArrayList<>();
            ListTag posNBTList = entryTag.getList("Positions", CompoundTag.TAG_COMPOUND);
            for (int j = 0; j < posNBTList.size(); j++) {
                CompoundTag vTag = posNBTList.getCompound(j);
                posList.add(new Vec2(vTag.getFloat("x"), vTag.getFloat("y")));
            }
            skillPos.put(name, posList);
        }

        System.out.println(skillPos);
    }
}
