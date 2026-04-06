package com.Polarice3.Goety.utils;

import com.Polarice3.Goety.common.capabilities.ModCapabilities;
import com.Polarice3.Goety.common.capabilities.witchbarter.IWitchBarter;
import com.Polarice3.Goety.common.capabilities.witchbarter.WitchBarterImp;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raider;

import javax.annotation.Nullable;

public class WitchBarterHelper {
    public static IWitchBarter getCapability(LivingEntity livingEntity) {
        return ModCapabilities.WITCH_BARTER.maybeGet(livingEntity).orElse(new WitchBarterImp());
    }

    public static int getTimer(Raider witch) {
        return getCapability(witch).getTimer();
    }

    public static void setTimer(Raider witch, int timer) {
        getCapability(witch).setTimer(timer);
        sendWitchBarterUpdatePacket(witch);
    }

    public static void decreaseTimer(Raider witch) {
        setTimer(witch, -1);
        sendWitchBarterUpdatePacket(witch);
    }

    @Nullable
    public static LivingEntity getTrader(Raider witch) {
        return witch.level.getEntity(getCapability(witch).getTraderID()) instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    public static void setTrader(Raider witch, @Nullable LivingEntity livingEntity) {
        if (livingEntity != null) {
            getCapability(witch).setTraderID(livingEntity.getId());
        } else {
            getCapability(witch).setTraderID(-1);
        }
        sendWitchBarterUpdatePacket(witch);
    }

    public static void sendWitchBarterUpdatePacket(Raider witch) {
        if (!witch.level.isClientSide()) {
            ModCapabilities.WITCH_BARTER.sync(witch);
        }
    }
}
