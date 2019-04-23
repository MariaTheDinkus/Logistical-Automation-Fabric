package com.zundrel.logisticalautomation.blocks;

import net.minecraft.block.Block;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;

public class BlockBase extends Block {
    public static final DirectionProperty FACING = Properties.FACING_HORIZONTAL;

    public BlockBase(Settings settings) {
        super(settings);

//        setCreativeTab(CreativeTabLogistical.INSTANCE);
//        setUnlocalizedName(LogisticalAutomation.MOD_ID + "." + unlocalizedName);
//        setRegistryName(unlocalizedName);
    }
}
