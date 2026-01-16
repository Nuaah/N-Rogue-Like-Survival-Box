package com.Nuaah.NRogueLikeSurvivalBox.regi;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import com.Nuaah.NRogueLikeSurvivalBox.item.ItemBrownBag;
import com.Nuaah.NRogueLikeSurvivalBox.item.ItemLegacySoul;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NRogueLikeSurvivalBoxItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NRogueLikeSurvivalBox.MOD_ID);

    public static final RegistryObject<Item> BROWN_BAG = ITEMS.register("brown_bag", ItemBrownBag::new);
    public static final RegistryObject<Item> MEAT_BONE = ITEMS.register("meat_bone", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_ARROWHEAD = ITEMS.register("stone_arrowhead", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_EYE = ITEMS.register("stone_eye", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LUMP_OF_ROTTEN_FLESH = ITEMS.register("lump_of_rotten_flesh", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GREEN_DYNAMITE = ITEMS.register("green_dynamite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOOD_RING = ITEMS.register("wood_ring", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POISON_FANG = ITEMS.register("poison_fang", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNISON_SEED = ITEMS.register("unison_seed", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEGACY_SOUL = ITEMS.register("legacy_soul", ItemLegacySoul::new);


    public static final RegistryObject<Item> WOOD_KID_SPAWN_EGG = ITEMS.register("wood_kid_spawn_egg",
            () -> new ForgeSpawnEggItem(NRogueLikeSurvivalBoxEntities.WOOD_KID,0x5f4a2b,0x917142,
                    new Item.Properties()));
    public static final RegistryObject<Item> ROCKY_SPAWN_EGG = ITEMS.register("rocky_spawn_egg",
            () -> new ForgeSpawnEggItem(NRogueLikeSurvivalBoxEntities.ROCKY,0x888788,0xb5b5b5,
                    new Item.Properties()));
    public static final RegistryObject<Item> DOODUS_SPAWN_EGG = ITEMS.register("doodus_spawn_egg",
            () -> new ForgeSpawnEggItem(NRogueLikeSurvivalBoxEntities.DOODUS,0x527d26,0x649832,
                    new Item.Properties()));
    public static final RegistryObject<Item> SPRING_WEED_SPAWN_EGG = ITEMS.register("spring_weed_spawn_egg",
            () -> new ForgeSpawnEggItem(NRogueLikeSurvivalBoxEntities.SPRING_WEED,0xbd603e,0x99741d,
                    new Item.Properties()));

    public static final RegistryObject<Item> CACTUS_NEEDLE_PROJECTILE = ITEMS.register("cactus_needle_projectile", () -> new Item(new Item.Properties()));


    //スキルツリー用
    public static final RegistryObject<Item> HEART = ITEMS.register("heart1", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BOOTS = ITEMS.register("boots1", () -> new Item(new Item.Properties()));
}
