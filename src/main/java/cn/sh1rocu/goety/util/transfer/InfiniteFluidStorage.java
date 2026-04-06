package cn.sh1rocu.goety.util.transfer;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.nbt.CompoundTag;

public class InfiniteFluidStorage extends SingleVariantStorage<FluidVariant> {

    private final FluidVariant fluidVariant;

    public InfiniteFluidStorage(FluidVariant variant) {
        this.amount = Long.MAX_VALUE;
        this.variant = variant;
        this.fluidVariant = variant;
    }

    @Override
    public FluidVariant getResource() {
        return fluidVariant;
    }

    @Override
    protected FluidVariant getBlankVariant() {
        return fluidVariant;
    }

    @Override
    public long getAmount() {
        return Long.MAX_VALUE;
    }

    @Override
    protected long getCapacity(FluidVariant variant) {
        return Long.MAX_VALUE;
    }

    @Override
    public long extract(FluidVariant extractedVariant, long maxAmount, TransactionContext transaction) {
        return super.extract(extractedVariant, maxAmount, transaction);
    }


    @Override
    public long insert(FluidVariant insertedVariant, long maxAmount, TransactionContext transaction) {
        return insertedVariant.isOf(fluidVariant.getFluid()) ? maxAmount : 0;
    }

    public void readNbt(CompoundTag nbt) {
        variant = FluidVariant.fromNbt(nbt.getCompound("variant"));
        amount = nbt.getLong("amount");
    }
}
