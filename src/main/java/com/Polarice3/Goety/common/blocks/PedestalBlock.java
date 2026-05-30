package com.Polarice3.Goety.common.blocks;

import com.Polarice3.Goety.common.blocks.entities.PedestalBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class PedestalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final VoxelShape SHAPE_BASE = Block.box(2.0D, 1.0D, 2.0D,
            14.0D, 2.0D, 14.0D);
    public static final VoxelShape SHAPE_PILLAR = Block.box(5.0D, 3.0D, 5.0D,
            11.0D, 12.0D, 11.0D);
    public static final VoxelShape SHAPE_HOLDER = Block.box(2.0D, 13.0D, 2.0D,
            14.0D, 15.0D, 14.0D);
    public static final VoxelShape SHAPE = Shapes.or(SHAPE_BASE, SHAPE_PILLAR, SHAPE_HOLDER);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

    public PedestalBlock() {
        this(ModBlocks.ShadeStoneProperties().noOcclusion());
    }

    public PedestalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(OCCUPIED, Boolean.FALSE));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);
            PedestalBlockEntity pedestal = (PedestalBlockEntity) world.getBlockEntity(pos);
            if (pedestal != null) {
                var handler = pedestal.itemStackHandler;
                if (handler != null) {
                    if (!player.isShiftKeyDown() && !player.isCrouching()) {
                        ItemStack itemStack = handler.getStackInSlot(0);
                        if (itemStack.isEmpty()) {
                            if (!pedestal.isLocked()) {
                                if (!heldItem.isEmpty()) {
                                    try (Transaction tx = Transaction.openOuter()) {
                                        var itemVariant = ItemVariant.of(heldItem);
                                        int before = heldItem.getCount();
                                        int inserted = (int) handler.insert(itemVariant, before, tx);
                                        tx.commit();
                                        int leftover = before - inserted;
                                        player.setItemInHand(hand, leftover > 0 ? itemVariant.toStack(leftover) : ItemStack.EMPTY);
                                        world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1, 1);
                                    }
                                }
                            } else {
                                world.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1, 1);
                            }
                        } else {
                            if (heldItem.isEmpty()) {
                                try (Transaction tx = Transaction.openOuter()) {
                                    var itemVariant = ItemVariant.of(itemStack);
                                    long extracted = handler.extract(itemVariant, 64, tx);
                                    tx.commit();
                                    player.setItemInHand(hand, extracted > 0 ? itemVariant.toStack((int) extracted) : ItemStack.EMPTY);
                                }
                            } else {
                                try (Transaction tx = Transaction.openOuter()) {
                                    var itemVariant = ItemVariant.of(itemStack);
                                    long extracted = handler.extract(itemVariant, 64, tx);
                                    tx.commit();
                                    if (extracted > 0) {
                                        PlayerInventoryStorage.of(player).offerOrDrop(itemVariant, extracted, tx);
                                    }

                                }
                            }
                            world.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1, 1);
                        }
                        pedestal.setChanged();
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity tileentity = pLevel.getBlockEntity(pPos);
            if (tileentity instanceof PedestalBlockEntity pedestalBlockEntity) {
                var storage = ItemStorage.SIDED.find(pLevel, pPos, pState, pedestalBlockEntity, null);
                if (storage != null) {
                    dropInventoryItems(tileentity.getLevel(), tileentity.getBlockPos(), storage);
                }
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public static void dropInventoryItems(Level worldIn, BlockPos pos, Storage<ItemVariant> itemHandler) {
        for (StorageView<ItemVariant> view : itemHandler.nonEmptyViews()) {
            Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), view.getResource().toStack((int) view.getAmount()));
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor iworld = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        boolean flag = iworld.getFluidState(blockpos).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        if (!pState.getValue(BlockStateProperties.WATERLOGGED) && pFluidState.getType() == Fluids.WATER) {
            pLevel.setBlock(pPos, pState.setValue(WATERLOGGED, Boolean.TRUE), 3);
            pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED, OCCUPIED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_151996_, BlockState p_151997_) {
        return new PedestalBlockEntity(p_151996_, p_151997_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152755_, BlockState p_152756_, BlockEntityType<T> p_152757_) {
        return (world, pos, state, blockEntity) -> {
            if (blockEntity instanceof PedestalBlockEntity pedestal)
                pedestal.tick();
        };
    }
}
/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */