package com.Polarice3.Goety.common.blocks.entities;

import cn.sh1rocu.goety.util.transfer.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends RitualBlockEntity {
    public ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (PedestalBlockEntity.this.isLocked()) {
                return 0;
            } else {
                return super.insert(resource, maxAmount, transaction);
            }
        }

        @Override
        protected void onContentsChanged(int slot) {
            if (PedestalBlockEntity.this.level != null) {
                if (!PedestalBlockEntity.this.level.isClientSide) {
                    boolean flag = !this.getStackInSlot(0).isEmpty();
                    PedestalBlockEntity.this.level.setBlockAndUpdate(PedestalBlockEntity.this.getBlockPos(),
                            PedestalBlockEntity.this.getBlockState().setValue(BlockStateProperties.OCCUPIED, flag));
                    PedestalBlockEntity.this.markNetworkDirty();
                }
            }
        }
    };
    public int locked = 0;

    public PedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.PEDESTAL, blockPos, blockState);
    }

    public PedestalBlockEntity(BlockEntityType<?> blockEntity, BlockPos blockPos, BlockState blockState) {
        super(blockEntity, blockPos, blockState);
    }

    public void tick() {
        if (this.locked > 0) {
            --this.locked;
        }
    }

    public int getLocked() {
        return this.locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return this.getLocked() > 0;
    }

    @Override
    public @Nullable ItemStackHandler getItemStorage(@Nullable Direction direction) {
        return this.itemStackHandler;
    }

    @Override
    public void readNetwork(CompoundTag compound) {
        this.itemStackHandler.deserializeNBT(compound.getCompound("inventory"));
        this.locked = compound.getInt("Locked");
    }

    @Override
    public CompoundTag writeNetwork(CompoundTag compound) {
        compound.put("inventory", this.itemStackHandler.serializeNBT());
        compound.putInt("Locked", this.locked);
        return compound;
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