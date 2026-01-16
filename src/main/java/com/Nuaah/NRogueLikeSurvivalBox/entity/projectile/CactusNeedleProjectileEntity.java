package com.Nuaah.NRogueLikeSurvivalBox.entity.projectile;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import com.Nuaah.NRogueLikeSurvivalBox.regi.NRogueLikeSurvivalBoxItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class CactusNeedleProjectileEntity extends ThrowableProjectile implements ItemSupplier {

    public CactusNeedleProjectileEntity(EntityType<? extends CactusNeedleProjectileEntity> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    public CactusNeedleProjectileEntity(EntityType<? extends CactusNeedleProjectileEntity> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        // 4.0F はハート2個分のダメージ
        target.hurt(this.damageSources().thrown(this, this.getOwner()), 2.0F);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(NRogueLikeSurvivalBoxItems.CACTUS_NEEDLE_PROJECTILE.get());
    }
}
