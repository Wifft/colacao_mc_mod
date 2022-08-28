package dev.wifft.colacaomod.entities.mobs.models;

import dev.wifft.colacaomod.entities.mobs.AbstractCoupleMob;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

import net.minecraft.util.Mth;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoupleMobModel<T extends AbstractCoupleMob> extends PlayerModel<AbstractCoupleMob> {
    public CoupleMobModel(ModelPart modelPart, boolean slim)
        {
            super(modelPart, slim);
        }

        @Override
        public void setupAnim(AbstractCoupleMob entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch)
        {
            super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, yaw, pitch);

            if (entity.isAngry()) {
                int attackAnimationTick = entity.getAttackAnimationTick();
                
                float rotAngle = attackAnimationTick > 0 
                    ? (-2.0f + 1.5f) * Mth.triangleWave((float) attackAnimationTick - ageInTicks, 10.0f)
                    : 30.0f;

                this.leftArm.xRot = this.rightArm.xRot = this.leftSleeve.xRot = this.rightSleeve.xRot = rotAngle;
            }
        }
}
