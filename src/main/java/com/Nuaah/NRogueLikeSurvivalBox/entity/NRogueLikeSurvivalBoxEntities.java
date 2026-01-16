package com.Nuaah.NRogueLikeSurvivalBox.entity;

import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.DoodusEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.RockyEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.SpringWeedEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.custom.WoodKidEntity;
import com.Nuaah.NRogueLikeSurvivalBox.entity.projectile.CactusNeedleProjectileEntity;
import com.Nuaah.NRogueLikeSurvivalBox.main.NRogueLikeSurvivalBox;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NRogueLikeSurvivalBoxEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NRogueLikeSurvivalBox.MOD_ID);

    public static final RegistryObject<EntityType<WoodKidEntity>> WOOD_KID =
            ENTITY_TYPES.register("wood_kid",() -> EntityType.Builder.of(WoodKidEntity::new, MobCategory.MONSTER)
                    .sized(0.8F,0.8F).build("wood_kid"));
    public static final RegistryObject<EntityType<RockyEntity>> ROCKY =
            ENTITY_TYPES.register("rocky",() -> EntityType.Builder.of(RockyEntity::new, MobCategory.MONSTER)
                    .sized(0.8F,0.8F).build("rocky"));
    public static final RegistryObject<EntityType<DoodusEntity>> DOODUS =
            ENTITY_TYPES.register("doodus",() -> EntityType.Builder.of(DoodusEntity::new, MobCategory.MONSTER)
                    .sized(0.8F,2F).build("doodus"));
    public static final RegistryObject<EntityType<SpringWeedEntity>> SPRING_WEED =
            ENTITY_TYPES.register("spring_weed",() -> EntityType.Builder.of(SpringWeedEntity::new, MobCategory.MONSTER)
                    .sized(0.8F,2F).build("spring_weed"));

    //弾
    public static final RegistryObject<EntityType<CactusNeedleProjectileEntity>> CACTUS_NEEDLE_PROJECTILE =
            ENTITY_TYPES.register("cactus_needle", () -> EntityType.Builder.<CactusNeedleProjectileEntity>of(CactusNeedleProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("cactus_needle"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
