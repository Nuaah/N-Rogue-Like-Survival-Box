package com.Nuaah.NRogueLikeSurvivalBox.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.EnumSet;

public class SpringWeedEntity extends Monster {

    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;

    public SpringWeedEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.moveControl = new SpringWeedMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        // 以下の2つのGoalが移動の要です
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new SlimeAttackGoal(this));
        this.goalSelector.addGoal(3, new SlimeKeepOnJumpingGoal(this));

        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // 接触した時の処理（スライムの挙動を再現）
    @Override
    public void playerTouch(Player player) {
        if (this.isActiveAttackTarget(player)) {
            this.dealDamage(player);
        }
    }

    // 攻撃の判定ロジック
    protected void dealDamage(LivingEntity target) {
        if (this.isAlive()) {
            // 攻撃間隔のチェック（バニラスライムと同様の距離判定）
            double distance = this.distanceToSqr(target);
            // 接触距離（サイズに応じて調整。1.0〜2.0程度が妥当）
            if (distance < 1.0D + (double)this.getBbWidth() && this.hasLineOfSight(target) && target.hurt(this.damageSources().mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                this.playSound(SoundEvents.GRASS_HIT, 1.0F, 1.0F);
                this.doEnchantDamageEffects(this, target);
            }
        }
    }

    // 攻撃対象として有効かどうかのチェック
    protected boolean isActiveAttackTarget(LivingEntity target) {
        return this.canAttack(target);
    }

    @Override
    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5F;
        this.oSquish = this.squish;
        super.tick();

        if (this.onGround() && !this.wasOnGround) {
            this.targetSquish = -0.5F;
            this.playSound(this.getSquishSound(), this.getSoundVolume(), this.getVoicePitch());
            this.level().addParticle(this.getParticleType(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        } else if (!this.onGround() && this.wasOnGround) {
            this.targetSquish = 1.0F;
        }

        this.wasOnGround = this.onGround();
        this.targetSquish *= 0.6F;
    }

    protected ParticleOptions getParticleType() { return ParticleTypes.ASH; }
    protected SoundEvent getSquishSound() { return SoundEvents.GRASS_STEP; }

    public static AttributeSupplier.Builder createAttribute() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D) // 前進速度
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    // --- 内部ロジック ---

    static class SpringWeedMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final SpringWeedEntity weed;
        private boolean isAggressive;

        public SpringWeedMoveControl(SpringWeedEntity weed) {
            super(weed);
            this.weed = weed;
            this.yRot = 180.0F * weed.getYRot() / (float)Math.PI;
        }

        public void setDirection(float yRot, boolean aggressive) {
            this.yRot = yRot;
            this.isAggressive = aggressive;
        }

        @Override
        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();

            // OperationがMOVE_TOでない場合（AIが止まっている時）は動かさない
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
                return;
            }

            this.operation = MoveControl.Operation.WAIT;
            if (this.mob.onGround()) {
                this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.jumpDelay-- <= 0) {
                    this.jumpDelay = this.weed.getRandom().nextInt(20) + (isAggressive ? 5 : 20);
                    this.weed.getJumpControl().jump();
                    this.weed.playSound(SoundEvents.SLIME_JUMP, 0.5F, 1.0F);
                    this.mob.setZza(this.mob.getSpeed()); // ジャンプ時に前進
                } else {
                    this.mob.setZza(0.0F);
                }
            } else {
                // 空中では前進を維持
                this.mob.setZza(this.mob.getSpeed());
            }
        }
    }

    static class SlimeAttackGoal extends net.minecraft.world.entity.ai.goal.Goal {
        private final SpringWeedEntity slime;

        public SlimeAttackGoal(SpringWeedEntity slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity target = this.slime.getTarget();
            return target != null && target.isAlive();
        }

        public void tick() {
            LivingEntity target = this.slime.getTarget();
            if (target != null) {
                this.slime.lookAt(target, 10.0F, 10.0F);
                if (this.slime.getMoveControl() instanceof SpringWeedMoveControl control) {
                    // ターゲットの方向をセットし、MOVE_TO状態にする
                    control.setDirection(this.slime.getYRot(), true);
                    control.setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0D);
                }
            }
        }
    }

    static class SlimeKeepOnJumpingGoal extends net.minecraft.world.entity.ai.goal.Goal {
        private final SpringWeedEntity slime;

        public SlimeKeepOnJumpingGoal(SpringWeedEntity slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() { return true; }

        public void tick() {
            if (this.slime.getMoveControl() instanceof SpringWeedMoveControl control) {
                // ターゲットがいない時でも、現在の向きにMOVE_TOを維持
                control.setWantedPosition(this.slime.getX() + this.slime.getLookAngle().x,
                        this.slime.getY(),
                        this.slime.getZ() + this.slime.getLookAngle().z, 1.0D);
            }
        }
    }
}