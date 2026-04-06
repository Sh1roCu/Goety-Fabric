package com.Polarice3.Goety.common.capabilities.witchbarter;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class WitchBarterImp implements IWitchBarter {

    private int timer;
    private int traderID = -1;

    @Override
    public int getTimer() {
        return timer;
    }

    @Override
    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public int getTraderID() {
        return this.traderID;
    }

    @Override
    public void setTraderID(int id) {
        this.traderID = id;
    }

    @Override
    public void readFromNbt(@NotNull CompoundTag compoundTag) {
        if (compoundTag.contains("barterTimer")) {
            this.setTimer(compoundTag.getInt("barterTimer"));
        }
        if (compoundTag.contains("barterTraderID")) {
            this.setTraderID(compoundTag.getInt("barterTraderID"));
        }
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag compoundTag) {
        compoundTag.putInt("barterTimer", this.getTimer());
        compoundTag.putInt("barterTraderID", this.getTraderID());
    }
}
