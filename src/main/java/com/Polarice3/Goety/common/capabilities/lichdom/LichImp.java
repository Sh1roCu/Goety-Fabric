package com.Polarice3.Goety.common.capabilities.lichdom;

import com.Polarice3.Goety.utils.LichdomHelper;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class LichImp implements ILichdom {

    private boolean lichdom;
    private boolean lichMode;
    private boolean nightVision;
    private int smited;
    private int lichModeColor = -1;

    @Override
    public boolean getLichdom() {
        return this.lichdom;
    }

    @Override
    public void setLichdom(boolean lichdom) {
        this.lichdom = lichdom;
    }

    @Override
    public boolean isLichMode() {
        return this.lichMode;
    }

    @Override
    public void setLichMode(boolean lichMode) {
        this.lichMode = lichMode;
    }

    @Override
    public boolean nightVision() {
        return this.nightVision;
    }

    @Override
    public void setNightVision(boolean nightVision) {
        this.nightVision = nightVision;
    }

    @Override
    public int smited() {
        return this.smited;
    }

    @Override
    public void setSmited(int smited) {
        this.smited = smited;
    }

    @Override
    public int lichModeColor() {
        return this.lichModeColor;
    }

    @Override
    public void setLichModeColor(int colorCode) {
        this.lichModeColor = colorCode;
    }

    @Override
    public void readFromNbt(@NotNull CompoundTag compoundTag) {
        LichdomHelper.load(compoundTag, this);
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag compoundTag) {
        LichdomHelper.save(compoundTag, this);
    }
}
