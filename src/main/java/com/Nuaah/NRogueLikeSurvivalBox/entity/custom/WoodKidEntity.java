package com.Nuaah.NRogueLikeSurvivalBox.entity.custom;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WoodKidEntity extends Monster {

    public WoodKidEntity(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    private void setupAnimationStates(){
        if (this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        // getPose() == Pose.STANDING の条件を一度外してみる
        // または、移動速度（f）の倍率を上げる
        f = Math.min(pPartialTick * 6.0F, 1.0F);

        // 第1引数に現在の速度、第2引数にスムージング（0.2F程度）
        this.walkAnimation.update(f, 0.2F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity enemy) {
                // 攻撃が届く距離（半径）を 4.0D に設定する場合
                // 計算式は (自分の幅 + 相手の幅 + 指定距離)^2
                return (double)(this.mob.getBbWidth() * this.mob.getBbWidth() * 1.0F + enemy.getBbWidth());
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity p_147273_) {
        return super.getMeleeAttackRangeSqr(p_147273_);
    }

    public static AttributeSupplier.Builder createAttribute(){
        return Monster.createLivingAttributes()
            .add(Attributes.FOLLOW_RANGE, 20.0D)
            .add(Attributes.MAX_HEALTH,14D)
            .add(Attributes.ARMOR,2D)
            .add(Attributes.MOVEMENT_SPEED,0.25D)
            .add(Attributes.ATTACK_DAMAGE,2F)
            .add(Attributes.ATTACK_KNOCKBACK,2F);
    }
}
