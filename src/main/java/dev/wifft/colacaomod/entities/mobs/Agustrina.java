package dev.wifft.colacaomod.entities.mobs;

import java.util.UUID;

import javax.annotation.Nullable;

import dev.wifft.colacaomod.entities.mobs.renderers.AbstractCoupleMobRenderer;

import net.minecraft.client.model.geom.ModelLayers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.EntityType;

import net.minecraft.world.entity.animal.Animal;

import net.minecraft.world.item.Items;

import net.minecraft.world.level.Level;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Agustrina extends AbstractCoupleMob {
    public static final String ENTITY_ID = "agustrina";

    @Nullable
    private UUID persistentAngerTarget;

    public Agustrina(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);

        super.setCustomName(Component.literal(ENTITY_ID));
    }

    @Override
    public void die(DamageSource damageSource)
    {
        spawnAtLocation(Items.POPPY);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Renderer extends AbstractCoupleMobRenderer {
        private static final ResourceLocation DEFAULT_LOCATION = new ResourceLocation("colacaomod:textures/entity/agustrina.png");
        private static final ResourceLocation ANGRY_LOCATION = new ResourceLocation("colacaomod:textures/entity/agustrina_angry.png");
    
        public Renderer(EntityRendererProvider.Context context)
        {
            super(context, ModelLayers.PLAYER_SLIM, true);
        }
    
        @Override
        public ResourceLocation getTextureLocation(AbstractCoupleMob entity)
        {
            return !entity.isAngry() ? DEFAULT_LOCATION : ANGRY_LOCATION;
        }
    }    
}
