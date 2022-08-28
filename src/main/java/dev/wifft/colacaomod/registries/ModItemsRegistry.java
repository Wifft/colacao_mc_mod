package dev.wifft.colacaomod.registries;

import dev.wifft.colacaomod.ColaCaoMod;

import dev.wifft.colacaomod.entities.items.AgustrinaSpawnEgg;
import dev.wifft.colacaomod.entities.items.ColacaoBucket;
import dev.wifft.colacaomod.entities.items.GorritocolacaoSpawnEgg;

import net.minecraft.world.item.Item;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ColaCaoMod.MOD_ID);

    public static final RegistryObject<Item> KOLAKAO_BUCKET = ITEMS.register(ColacaoBucket.ENTITY_ID, () -> new ColacaoBucket());
    public static final RegistryObject<Item> GORRITOCOLACAO_SPAWN_EGG = ITEMS.register(GorritocolacaoSpawnEgg.ENTITY_ID, () -> new GorritocolacaoSpawnEgg());
    public static final RegistryObject<Item> AGUSTRINA_SPAWN_EGG = ITEMS.register(AgustrinaSpawnEgg.ENTITY_ID, () -> new AgustrinaSpawnEgg());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
