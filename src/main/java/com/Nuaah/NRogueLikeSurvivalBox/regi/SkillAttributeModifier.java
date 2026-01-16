package com.Nuaah.NRogueLikeSurvivalBox.regi;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SkillAttributeModifier {

    private static final UUID SKILL_HEALTH_BOOST_UUID = UUID.fromString("f1e2d3c4-b5a6-47d8-9e0f-1a2b3c4d5e6f");
    private static final UUID SKILL_ATTACK_BOOST_UUID = UUID.fromString("3d95a1c2-0f7b-4ea3-9c48-6b2e7d0f5a91");
    private static final UUID SKILL_ARMOR_BOOST_UUID = UUID.fromString("c8b3e2d5-9f41-4a6b-8c23-1e7d9a50b4f1");
    private static final UUID SKILL_MOVEMENT_SPEED_BOOST_UUID = UUID.fromString("7a64c2f1-3bd9-4c8a-9e15-2b4f7a9c3d82");

    public static void checkSkill(ServerPlayer player, String skillName, float skillValue){
        switch (skillName){
            case "add_health":
                addAttributeModifier(player, Attributes.MAX_HEALTH,"Skill Health Boost",skillValue,SKILL_HEALTH_BOOST_UUID);
                break;

            case "add_attack":
                addAttributeModifier(player, Attributes.ATTACK_DAMAGE,"Skill Attack Boost",skillValue,SKILL_ATTACK_BOOST_UUID);
                break;

            case "add_armor":
                addAttributeModifier(player, Attributes.ARMOR,"Skill Armor Boost",skillValue * 0.25F,SKILL_ARMOR_BOOST_UUID);
                break;

            case "add_movement_speed":
                addAttributeModifier(player, Attributes.MOVEMENT_SPEED,"Skill Movement Speed Boost",skillValue * 0.001F,SKILL_MOVEMENT_SPEED_BOOST_UUID);
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

}
