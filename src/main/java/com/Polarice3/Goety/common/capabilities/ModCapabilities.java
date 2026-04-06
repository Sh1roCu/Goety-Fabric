package com.Polarice3.Goety.common.capabilities;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.capabilities.lichdom.ILichdom;
import com.Polarice3.Goety.common.capabilities.lichdom.LichImp;
import com.Polarice3.Goety.common.capabilities.misc.IMisc;
import com.Polarice3.Goety.common.capabilities.misc.MiscImp;
import com.Polarice3.Goety.common.capabilities.soulenergy.ISoulEnergy;
import com.Polarice3.Goety.common.capabilities.soulenergy.SEImp;
import com.Polarice3.Goety.common.capabilities.witchbarter.IWitchBarter;
import com.Polarice3.Goety.common.capabilities.witchbarter.WitchBarterImp;
import com.Polarice3.Goety.common.entities.hostile.cultists.Cultist;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import org.jetbrains.annotations.NotNull;

public class ModCapabilities implements EntityComponentInitializer {

    public static final ComponentKey<ILichdom> LICH_DOM = ComponentRegistry.getOrCreate(Goety.location("lichdom"), ILichdom.class);
    public static final ComponentKey<IMisc> MISC = ComponentRegistry.getOrCreate(Goety.location("misc"), IMisc.class);
    public static final ComponentKey<ISoulEnergy> SOUL_ENERGY = ComponentRegistry.getOrCreate(Goety.location("soulenergy"), ISoulEnergy.class);
    public static final ComponentKey<IWitchBarter> WITCH_BARTER = ComponentRegistry.getOrCreate(Goety.location("witchbarter"), IWitchBarter.class);

    @Override
    public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(LICH_DOM, player -> new LichImp(), RespawnCopyStrategy.ALWAYS_COPY);

        registry.registerFor(LivingEntity.class, MISC, entity -> new MiscImp());
        registry.registerForPlayers(MISC, player -> new MiscImp(), RespawnCopyStrategy.ALWAYS_COPY);

        registry.registerForPlayers(SOUL_ENERGY, player -> new SEImp(), RespawnCopyStrategy.ALWAYS_COPY);

        registry.registerFor(Witch.class, WITCH_BARTER, witch -> new WitchBarterImp());
        registry.registerFor(Cultist.class, WITCH_BARTER, cultist -> new WitchBarterImp());

    }
}
