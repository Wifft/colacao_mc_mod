package dev.wifft.colacaomod.entities.mobs;

import java.util.ArrayList;
 import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

import net.minecraft.server.level.ServerLevel;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import net.minecraft.util.TimeUtil;

import net.minecraft.util.valueproviders.UniformInt;

import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;

import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.pathfinder.BlockPathTypes;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


public abstract class AbstractCoupleMob extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Gorritocolacao.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    private static final float SPEED = 0.6f;
    
    public Level level;

    private int attackAnimationTick = 0;

    private List<AbstractCoupleMob> nearbyCouples = new ArrayList<AbstractCoupleMob>();

    @Nullable
    private UUID persistentAngerTarget;

    public AbstractCoupleMob(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
        
        this.level = level;

        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0f);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0f);

        this.setCustomNameVisible(true);
    }

    public static AttributeSupplier.Builder setCustomAttributes()
    {
        return Mob.createMobAttributes()
            .add(Attributes.MOVEMENT_SPEED, (double) 0.4f)
            .add(Attributes.MAX_HEALTH, 20.0d)
            .add(Attributes.ATTACK_DAMAGE, 4.0d)
            .add(Attributes.ATTACK_KNOCKBACK, 2.0f);
    }

    @Override
    public void registerGoals()
    {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbstractCoupleMob.CouplePanicGoal(SPEED + 0.2f));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, SPEED));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, SPEED - 0.2f));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, SPEED + 0.2f, true));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 4.0f));
        
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }
    
    @Override
    public void tick() {
        super.tick();

        this.nearbyCouples = this.getNearbyCouples(this.position());

        if (this.getLevel() != null && this.nearbyCouples != null) {
            for (AbstractCoupleMob couple : this.nearbyCouples) {
                Goal followMobGoal = new FollowMobGoal(couple, (double) SPEED, 1.0f, 32.0f);

                this.targetSelector.addGoal(6, followMobGoal);
            }
        }
    }

    @Override
    public void aiStep()
    {
        super.aiStep();

        if (this.attackAnimationTick > 0) {
            this.attackAnimationTick--;
        }

        if (!this.level.isClientSide && !super.isPathFinding() && super.onGround) {
           this.level.broadcastEntityEvent(this, (byte) 8);
        }
  
        if (!this.level.isClientSide) {
           this.updatePersistentAnger((ServerLevel) this.level, true);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public void handleEntityEvent(byte eventId) {
        super.handleEntityEvent(eventId);

        if (eventId == 4) {
            this.attackAnimationTick = 10;

            this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 1.0f, 1.0f);
        }
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        if (this.isAngry()) return SoundEvents.VILLAGER_NO;

        return SoundEvents.VILLAGER_AMBIENT;
    }
    
    @Override
    @Nullable
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.PLAYER_DEATH;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.PLAYER_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state)
    {
        super.playSound(SoundEvents.ZOMBIE_STEP, 0.15f, 1.0f);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public abstract void die(DamageSource damageSource);

    @Override
    public boolean hurt(DamageSource damageSource, float damageA) {
        boolean isCouple = damageSource.getEntity() instanceof AbstractCoupleMob;
        if (isCouple) return false;

        return super.hurt(damageSource, damageA);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        this.attackAnimationTick = 10;
        this.level.broadcastEntityEvent(this, (byte) 4);

        float damage = this.getAttackDamage();
        float damageA = (int) damage > 0 
            ? damage / 2.0f + (float) this.random.nextInt((int) damage)
            : damage;

        boolean success = target.hurt(DamageSource.mobAttack(this), damageA);

        return success;
    }

    public int getAttackAnimationTick()
    {
        return this.attackAnimationTick;
    }

    @Override
    public int getRemainingPersistentAngerTime()
    {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time)
    {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Override
    public void startPersistentAngerTimer()
    {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(super.random));
    }
  
    @Override
    @Nullable
    public UUID getPersistentAngerTarget()
    {
        return this.persistentAngerTarget;
    }
  
    @Override
    public void setPersistentAngerTarget(@Nullable UUID targetUuid) {
        this.persistentAngerTarget = targetUuid;
    }

    private List<AbstractCoupleMob> getNearbyCouples(Vec3 currentPos)
    {
        double followRange = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        currentPos = currentPos.add(followRange, followRange, followRange);

        AABB searchArea = this.getBoundingBox().inflate(currentPos.x, currentPos.y, currentPos.z);

        return this.getLevel().getEntitiesOfClass(AbstractCoupleMob.class, searchArea);
    }

    private float getAttackDamage()
    {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    private class CouplePanicGoal extends PanicGoal {
        public CouplePanicGoal(double speed)
        {
            super(AbstractCoupleMob.this, speed);
        }

        @Override
        protected boolean shouldPanic()
        {
            return !AbstractCoupleMob.this.isAngry() && (this.mob.isFreezing() || this.mob.isOnFire());
        }
    }
}
