package dev.wifft.colacaomod.entities.mobs.renderers;

import dev.wifft.colacaomod.entities.mobs.AbstractCoupleMob;
import dev.wifft.colacaomod.entities.mobs.models.CoupleMobModel;

import net.minecraft.client.model.HumanoidModel;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;

public abstract class AbstractCoupleMobRenderer extends HumanoidMobRenderer<AbstractCoupleMob, HumanoidModel<AbstractCoupleMob>> {
    public AbstractCoupleMobRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation,  boolean slim)
    {
        super(context, createModel(context.getModelSet(), modelLayerLocation, slim), 0.5f);
    }

    protected static CoupleMobModel<AbstractCoupleMob> createModel(EntityModelSet entityModelSet, ModelLayerLocation modelLayerLocation, boolean slim)
    {
        CoupleMobModel<AbstractCoupleMob> model = new CoupleMobModel<AbstractCoupleMob>(entityModelSet.bakeLayer(modelLayerLocation), slim);

        return model;
    } 
}
