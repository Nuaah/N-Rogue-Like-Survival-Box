package com.Nuaah.NRogueLikeSurvivalBox.entity.custom;

import com.Nuaah.NRogueLikeSurvivalBox.entity.NRogueLikeSurvivalBoxEntities;
import com.Nuaah.NRogueLikeSurvivalBox.entity.projectile.CactusNeedleProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DoodusEntity extends Monster implements RangedAttackMob {

    public DoodusEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // ★遠距離攻撃AI
        // 第2引数のスピードを「0.0D」にすることで、追いかけてこなくなります
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 0.0D, 40, 15.0F));

        // ターゲットを見つけるAI（これがないと狙ってくれません）
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity p_147273_) {
        return super.getMeleeAttackRangeSqr(p_147273_);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {

        if (this.distanceToSqr(target) > 225.0D) { // 15 * 15 = 225
            return;
        }

        CactusNeedleProjectileEntity needle = new CactusNeedleProjectileEntity(
                NRogueLikeSurvivalBoxEntities.CACTUS_NEEDLE_PROJECTILE.get(), this, this.level()
        );

        // 2. ターゲットの方向を計算
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.3333333333333333D) - needle.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);

        // 3. 弾を飛ばす（第4引数が速度、第5引数がバラつき/精度）
        needle.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(12 - this.level().getDifficulty().getId() * 4));

        // 4. 効果音を鳴らす
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

        // 5. ワールドに弾を出す
        this.level().addFreshEntity(needle);
    }

    public static AttributeSupplier.Builder createAttribute(){
        return Monster.createLivingAttributes()
            .add(Attributes.FOLLOW_RANGE,15D)
            .add(Attributes.MAX_HEALTH,20D)
            .add(Attributes.KNOCKBACK_RESISTANCE,1D)
            .add(Attributes.MOVEMENT_SPEED, 0.5D)
            .add(Attributes.ATTACK_DAMAGE,1F)
            .add(Attributes.ATTACK_KNOCKBACK,2F);
    }
}
