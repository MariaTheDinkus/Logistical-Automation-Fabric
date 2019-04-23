package com.zundrel.logisticalautomation.registries;

import com.zundrel.logisticalautomation.LogisticalAutomation;
import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.blocks.conveyors.BlockConveyor;
import com.zundrel.logisticalautomation.blocks.conveyors.BlockConveyorRamp;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block.Settings CONVEYOR_SETTINGS = FabricBlockSettings.of(Material.METAL).build();

    public static final BlockConveyor conveyor_normal = new BlockConveyor(CONVEYOR_SETTINGS, EnumConveyorType.NORMAL, EnumConveyorTier.NORMAL);
    public static final BlockConveyor conveyor_fast = new BlockConveyor(CONVEYOR_SETTINGS, EnumConveyorType.NORMAL, EnumConveyorTier.FAST);
    public static final BlockConveyor conveyor_express = new BlockConveyor(CONVEYOR_SETTINGS, EnumConveyorType.NORMAL, EnumConveyorTier.EXPRESS);

    public static final BlockConveyorRamp conveyor_ramp_normal = new BlockConveyorRamp(CONVEYOR_SETTINGS, EnumConveyorType.RAMP, EnumConveyorTier.NORMAL);
    public static final BlockConveyorRamp conveyor_ramp_fast = new BlockConveyorRamp(CONVEYOR_SETTINGS, EnumConveyorType.RAMP, EnumConveyorTier.FAST);
    public static final BlockConveyorRamp conveyor_ramp_express = new BlockConveyorRamp(CONVEYOR_SETTINGS, EnumConveyorType.RAMP, EnumConveyorTier.EXPRESS);

    public static void register() {
        registerBlock("conveyor_normal", conveyor_normal);
        registerBlock("conveyor_fast", conveyor_fast);
        registerBlock("conveyor_express", conveyor_express);

        registerBlock("conveyor_ramp_normal", conveyor_ramp_normal);
        registerBlock("conveyor_ramp_fast", conveyor_ramp_fast);
        registerBlock("conveyor_ramp_express", conveyor_ramp_express);
    }

    public static void registerBlock(String registryName, Block block) {
        Identifier identifier = new Identifier(LogisticalAutomation.MOD_ID, registryName);

        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().itemGroup(ItemGroup.MISC).itemGroup(LogisticalAutomation.ITEM_GROUP)));
    }
}