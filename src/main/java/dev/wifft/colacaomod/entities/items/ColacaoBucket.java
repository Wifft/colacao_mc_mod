package dev.wifft.colacaomod.entities.items;

import net.minecraft.world.food.FoodProperties;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ColacaoBucket extends Item {
    public static final String ENTITY_ID = "colacao_bucket";

    public ColacaoBucket() {
        super(
            new Item.Properties()
                .tab(CreativeModeTab.TAB_FOOD)
                .food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(0.5f)
                    .build()
                )
        );
    }
}
