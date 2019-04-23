package com.zundrel.logisticalautomation.blocks;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.api.IConveyor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockConveyorBase extends BlockBase implements IConveyor {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty HOPPER = BooleanProperty.create("hopper");

    private final EnumConveyorType conveyorType;
    private final EnumConveyorTier conveyorTier;

    public BlockConveyorBase(Settings settings, EnumConveyorType conveyorType, EnumConveyorTier conveyorTier) {
        super(settings);

        this.conveyorType = conveyorType;
        this.conveyorTier = conveyorTier;
    }

    @Override
    public EnumConveyorType getConveyorType() {
        return conveyorType;
    }

    @Override
    public EnumConveyorTier getConveyorTier() {
        return conveyorTier;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.with(FACING, NORTH, EAST, SOUTH, WEST, UP, HOPPER);
    }

    public BlockState setSideFromDirection(boolean value, Direction direction, BlockState blockState) {
        switch (direction) {
            case NORTH:
                return blockState.with(NORTH, value);
            case EAST:
                return blockState.with(EAST, value);
            case SOUTH:
                return blockState.with(SOUTH, value);
            case WEST:
                return blockState.with(WEST, value);
            case UP:
                return blockState.with(UP, value);
            default:
                return blockState;
        }
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity) {
        if (entity instanceof ItemEntity)
            entity.stepHeight = 0.5F;
    }
}
