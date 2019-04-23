package com.zundrel.logisticalautomation.blocks.conveyors;

import com.zundrel.logisticalautomation.api.EnumConveyorTier;
import com.zundrel.logisticalautomation.api.EnumConveyorType;
import com.zundrel.logisticalautomation.blocks.BlockConveyorBase;
import com.zundrel.logisticalautomation.utilities.MovementUtilities;
import com.zundrel.logisticalautomation.utilities.RotationUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.VerticalEntityPosition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockConveyorRamp extends BlockConveyorBase {
    public BlockConveyorRamp(Settings settings, EnumConveyorType conveyorType, EnumConveyorTier conveyorTier) {
        super(settings, conveyorType, conveyorTier);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        BlockState state = this.getDefaultState();
        Direction facing = itemPlacementContext.isPlayerSneaking() ? itemPlacementContext.getPlayerHorizontalFacing().getOpposite() : itemPlacementContext.getPlayerHorizontalFacing();

        return state
                .with(FACING, facing)
                .with(DOWN, itemPlacementContext.isPlayerSneaking());
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.with(FACING, DOWN);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, VerticalEntityPosition verticalEntityPosition_1) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, VerticalEntityPosition verticalEntityPosition) {
        VoxelShape ramp = VoxelShapes.empty();

        int scale = 4;

        for (int i = 0; i < scale; i++) {
            BoundingBox box = new BoundingBox(0, ((16F / scale) / 16F) * i, ((16F / scale) / 16F) * i, 1, ((((16F / scale) / 16F) * i) + ((16F / scale) / 16F)) - (1F / 16F), (((16F / scale) / 16F) * i) + ((16F / scale) / 16F));

            box = RotationUtilities.getRotatedBoundingBox(box, blockState.get(FACING));

            ramp = VoxelShapes.union(ramp, VoxelShapes.cuboid(box));
        }

        return ramp;
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

        MovementUtilities.pushEntity(entity, blockPos, getConveyorTier().getSpeed(), blockState.get(DOWN) ? blockState.get(FACING).getOpposite() : blockState.get(FACING));
    }
}