package com.zundrel.logisticalautomation;

import com.zundrel.logisticalautomation.registries.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class LogisticalAutomation implements ModInitializer
{
    public static final String MOD_ID = "logisticalautomation";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(ModBlocks.conveyor_normal));

    @Override
    public void onInitialize() {
        ModBlocks.register();
    }
}
