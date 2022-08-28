package dev.wifft.colacaomod.registries;

import dev.wifft.colacaomod.ColaCaoMod;
import dev.wifft.colacaomod.entities.mobs.Agustrina;
import dev.wifft.colacaomod.entities.mobs.Gorritocolacao;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ColaCaoMod.MOD_ID);

    public static final RegistryObject<EntityType<Gorritocolacao>> GORRITOCOLACAO = ENTITY_TYPES.register(
        Gorritocolacao.ENTITY_ID, 
        () -> EntityType.Builder
            .of(Gorritocolacao::new, MobCategory.CREATURE)
            .build(new ResourceLocation(ColaCaoMod.MOD_ID, Gorritocolacao.ENTITY_ID).toString())
    );
    
    public static final RegistryObject<EntityType<Agustrina>> AGUSTRINA = ENTITY_TYPES.register(
        Agustrina.ENTITY_ID,
        () -> EntityType.Builder
            .of(Agustrina::new, MobCategory.CREATURE)
            .build(new ResourceLocation(ColaCaoMod.MOD_ID, Agustrina.ENTITY_ID).toString())
    );

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}