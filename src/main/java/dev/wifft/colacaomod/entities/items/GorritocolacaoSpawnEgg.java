package dev.wifft.colacaomod.entities.items;

import dev.wifft.colacaomod.registries.ModEntityRegistry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MaterialColor;

import net.minecraftforge.common.ForgeSpawnEggItem;

public class GorritocolacaoSpawnEgg extends ForgeSpawnEggItem {
    public static final String ENTITY_ID = "gorritocolacao_spawn_egg";

    public GorritocolacaoSpawnEgg()
    {
        super(ModEntityRegistry.GORRITOCOLACAO, MaterialColor.COLOR_BROWN.col, MaterialColor.COLOR_RED.col, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }
}
