package com.Polarice3.Goety.common.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;

public class ArcaTeleporter extends PortalInfo {

    public ArcaTeleporter(Vec3 targetPos, Entity entity) {
        super(targetPos, Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }
}
