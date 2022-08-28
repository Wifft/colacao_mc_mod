package dev.wifft.colacaomod;

import dev.wifft.colacaomod.entities.mobs.Agustrina;
import dev.wifft.colacaomod.entities.mobs.Gorritocolacao;

import dev.wifft.colacaomod.registries.ModEntityRegistry;
import dev.wifft.colacaomod.registries.ModItemsRegistry;

import net.minecraftforge.client.event.EntityRenderersEvent;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ColaCaoMod.MOD_ID)
public class ColaCaoMod
{
    public static final String MOD_ID = "colacaomod";

    public ColaCaoMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItemsRegistry.register(modEventBus);
        ModEntityRegistry.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void entityAttibuteEvent(EntityAttributeCreationEvent event)
        {
            event.put(ModEntityRegistry.AGUSTRINA.get(), Agustrina.setCustomAttributes().build());
            event.put(ModEntityRegistry.GORRITOCOLACAO.get(), Gorritocolacao.setCustomAttributes().build());
        }

        @SubscribeEvent
        public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(ModEntityRegistry.AGUSTRINA.get(), Agustrina.Renderer::new);
            event.registerEntityRenderer(ModEntityRegistry.GORRITOCOLACAO.get(), Gorritocolacao.Renderer::new);
        }
    }
}
