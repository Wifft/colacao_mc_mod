package dev.wifft.colacaomod.entities.items;

import dev.wifft.colacaomod.registries.ModEntityRegistry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.minecraft.world.level.material.MaterialColor;

import net.minecraftforge.common.ForgeSpawnEggItem;

public class AgustrinaSpawnEgg extends ForgeSpawnEggItem {
    public static final String ENTITY_ID = "agustrina_spawn_egg";

    public AgustrinaSpawnEgg()
    {
        super(ModEntityRegistry.AGUSTRINA, MaterialColor.COLOR_BROWN.col, MaterialColor.COLOR_PINK.col, new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }
}
