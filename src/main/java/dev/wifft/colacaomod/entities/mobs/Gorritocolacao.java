package dev.wifft.colacaomod.entities.mobs;

import dev.wifft.colacaomod.entities.mobs.renderers.AbstractCoupleMobRenderer;

import dev.wifft.colacaomod.registries.ModItemsRegistry;

import net.minecraft.client.model.geom.ModelLayers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.EntityType;

import net.minecraft.world.entity.animal.Animal;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Gorritocolacao extends AbstractCoupleMob {
    public static final String ENTITY_ID = "gorritocolacao";

    public Gorritocolacao(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
        
        super.setCustomName(Component.literal(ENTITY_ID));
    }

    @Override
    public void die(DamageSource damageSource)
    {
        level.explode(this, super.getX(), super.getY(), super.getZ(), 4.0f, Explosion.BlockInteraction.NONE);

        spawnAtLocation(ModItemsRegistry.AGUSTRINA_SPAWN_EGG.get());
        dropWifftHead();
        spawnAtLocation(Items.ENCHANTED_GOLDEN_APPLE);
    }

    private void dropWifftHead()
    {
        ItemStack skull = new ItemStack(Blocks.PLAYER_HEAD, 1);
        
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("SkullOwner", "Wifft");

        skull.setTag(compoundTag);

        spawnAtLocation(skull);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Renderer extends AbstractCoupleMobRenderer {
        private static final ResourceLocation DEFAULT_LOCATION = new ResourceLocation("colacaomod:textures/entity/gorritocolacao.png");
        private static final ResourceLocation ANGRY_LOCATION = new ResourceLocation("colacaomod:textures/entity/gorritocolacao_angry.png");

        public Renderer(EntityRendererProvider.Context context)
        {
            super(context, ModelLayers.PLAYER, false);
        }

        @Override
        public ResourceLocation getTextureLocation(AbstractCoupleMob entity)
        {
            return !entity.isAngry() ? DEFAULT_LOCATION : ANGRY_LOCATION;
        }
    }
}
