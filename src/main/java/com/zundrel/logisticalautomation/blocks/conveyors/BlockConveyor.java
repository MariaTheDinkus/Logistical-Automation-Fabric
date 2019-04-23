package com.zundrel.logisticalautomation.blocks.conveyors;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.blocks.BlockConveyorBase;
import com.zundrel.logisticalautomation.utilities.MovementUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.VerticalEntityPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockConveyor extends BlockConveyorBase {
    public BlockConveyor(Settings settings, EnumConveyorType conveyorType, EnumConveyorTier conveyorTier) {
        super(settings, conveyorType, conveyorTier);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        BlockState state = this.getDefaultState();
        Direction facing = itemPlacementContext.isPlayerSneaking() ? itemPlacementContext.getPlayerHorizontalFacing().getOpposite() : itemPlacementContext.getPlayerHorizontalFacing();
        state = setSideFromDirection(false, facing, state);

        return state
                .with(FACING, facing)
                .with(UP, false)
                .with(HOPPER, false);
    }

    @Override
    public void neighborUpdate(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos fromBlockPos, boolean bool) {
        BlockState newState = blockState;
        Direction facing = blockState.get(Properties.FACING_HORIZONTAL);
        Direction right = facing.rotateYClockwise();
        Direction back = facing.getOpposite();
        Direction left = facing.rotateYCounterclockwise();

        BlockPos facingPos = blockPos.offset(facing);
        BlockPos rightPos = blockPos.offset(right);
        BlockPos backPos = blockPos.offset(back);
        BlockPos leftPos = blockPos.offset(left);
        BlockPos topPos = blockPos.up();

        if (world.getBlockEntity(facingPos) instanceof Inventory)
            newState = newState.with(HOPPER, true);
        else
            newState = newState.with(HOPPER, false);

        if (world.getBlockState(topPos).getBlock() instanceof BlockConveyor)
            newState = setSideFromDirection(true, Direction.UP, newState);
        else
            newState = setSideFromDirection(false, Direction.UP, newState);

        if (world.getBlockState(rightPos).getBlock() instanceof BlockConveyor && world.getBlockState(rightPos).get(FACING) == left)
            newState = setSideFromDirection(false, right, newState);
        else if (world.getBlockState(rightPos).getBlock() instanceof BlockConveyorRamp && world.getBlockState(rightPos).get(DOWN) && world.getBlockState(rightPos).get(FACING) == right)
            newState = setSideFromDirection(false, right, newState);
        else if (world.getBlockState(rightPos.down()).getBlock() instanceof BlockConveyorRamp && world.getBlockState(rightPos.down()).get(FACING) == left)
            newState = setSideFromDirection(false, right, newState);
        else
            newState = setSideFromDirection(true, right, newState);

        if (world.getBlockState(leftPos).getBlock() instanceof BlockConveyor && world.getBlockState(leftPos).get(FACING) == right)
            newState = setSideFromDirection(false, left, newState);
        else if (world.getBlockState(leftPos).getBlock() instanceof BlockConveyorRamp && world.getBlockState(leftPos).get(DOWN) && world.getBlockState(leftPos).get(FACING) == left)
            newState = setSideFromDirection(false, left, newState);
        else if (world.getBlockState(leftPos.down()).getBlock() instanceof BlockConveyorRamp && world.getBlockState(leftPos.down()).get(FACING) == right)
            newState = setSideFromDirection(false, left, newState);
        else
            newState = setSideFromDirection(true, left, newState);

        if (world.getBlockState(backPos).getBlock() instanceof BlockConveyor && world.getBlockState(backPos).get(FACING) == facing)
            newState = setSideFromDirection(false, back, newState);
        else if (world.getBlockState(backPos).getBlock() instanceof BlockConveyorRamp && world.getBlockState(backPos).get(DOWN) && world.getBlockState(backPos).get(FACING) == back)
            newState = setSideFromDirection(false, back, newState);
        else if (world.getBlockState(backPos.down()).getBlock() instanceof BlockConveyorRamp && world.getBlockState(backPos.down()).get(FACING) == facing)
            newState = setSideFromDirection(false, back, newState);
        else
            newState = setSideFromDirection(true, back, newState);

        world.setBlockState(blockPos, newState);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState stateFrom, boolean bool) {
        for (Direction dir : Direction.values()) {
            world.updateNeighborsAlways(pos.offset(dir), this);
        }
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState stateFrom, boolean b) {
        for (Direction dir : Direction.values()) {
            world.updateNeighborsAlways(pos.offset(dir), this);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, VerticalEntityPosition verticalEntityPosition_1) {
        return VoxelShapes.cuboid(0, 0, 0, 1, (1F / 16F), 1);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, VerticalEntityPosition verticalEntityPosition) {
        return VoxelShapes.cuboid(0, 0, 0, 1, (2F / 16F), 1);
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity) {
        super.onEntityCollision(blockState, world, blockPos, entity);

        if (entity instanceof LivingEntity && !entity.onGround)
            return;

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (!player.getMainHandStack().isEmpty() && Block.getBlockFromItem(player.getMainHandStack().getItem()) instanceof BlockConveyorBase) {
                return;
            } else if (!player.getOffHandStack().isEmpty() && Block.getBlockFromItem(player.getOffHandStack().getItem()) instanceof BlockConveyorBase) {
                return;
            }
        }

        MovementUtilities.pushEntity(entity, blockPos, getConveyorTier().getSpeed(), blockState.get(FACING));
    }
}