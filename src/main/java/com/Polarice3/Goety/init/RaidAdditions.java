package com.Polarice3.Goety.init;


import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.config.MobsConfig;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RaidAdditions {

    public static void init() {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();

        String raiderType = mappingResolver.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
        String entityTypeType = "L" + mappingResolver.mapClassName("intermediary", "net.minecraft.class_1299") + ";";
        var builder = ClassTinkerers.enumBuilder(raiderType, entityTypeType, int[].class);

        if (MobsConfig.WarlockRaid.get()) {
            builder.addEnum("GOETY_WARLOCK", () -> new Object[]{ModEntityType.WARLOCK, getWaves(MobsConfig.WarlockRaidCount.get())});
        }
        if (MobsConfig.MaverickRaid.get()) {
            builder.addEnum("GOETY_MAVERICK", () -> new Object[]{ModEntityType.MAVERICK, getWaves(MobsConfig.MaverickRaidCount.get())});
        }
        if (MobsConfig.HereticRaid.get()) {
            builder.addEnum("GOETY_HERETIC", () -> new Object[]{ModEntityType.HERETIC, getWaves(MobsConfig.HereticRaidCount.get())});
        }
        if (MobsConfig.IllagerRaid.get()) {
            if (MobsConfig.PikerRaid.get()) {
                builder.addEnum("GOETY_PIKER", () -> new Object[]{ModEntityType.PIKER, getWaves(MobsConfig.PikerRaidCount.get())});
            }
            if (MobsConfig.RipperRaid.get()) {
                builder.addEnum("GOETY_RIPPER", () -> new Object[]{ModEntityType.RIPPER, getWaves(MobsConfig.RipperRaidCount.get())});
            }
            if (MobsConfig.CrusherRaid.get()) {
                builder.addEnum("GOETY_CRUSHER", () -> new Object[]{ModEntityType.CRUSHER, getWaves(MobsConfig.CrusherRaidCount.get())});
            }
            if (MobsConfig.StormCasterRaid.get()) {
                builder.addEnum("GOETY_STORM_CASTER", () -> new Object[]{ModEntityType.STORM_CASTER, getWaves(MobsConfig.StormCasterRaidCount.get())});
            }
            if (MobsConfig.CryologerRaid.get()) {
                builder.addEnum("GOETY_CRYOLOGER", () -> new Object[]{ModEntityType.CRYOLOGER, getWaves(MobsConfig.CryologerRaidCount.get())});
            }
            if (MobsConfig.PreacherRaid.get()) {
                builder.addEnum("GOETY_PREACHER", () -> new Object[]{ModEntityType.PREACHER, getWaves(MobsConfig.PreacherRaidCount.get())});
            }
            if (MobsConfig.ConquillagerRaid.get()) {
                builder.addEnum("GOETY_CONQUILLAGER", () -> new Object[]{ModEntityType.CONQUILLAGER, getWaves(MobsConfig.ConquillagerRaidCount.get())});
            }
            if (MobsConfig.InquillagerRaid.get()) {
                builder.addEnum("GOETY_INQUILLAGER", () -> new Object[]{ModEntityType.INQUILLAGER, getWaves(MobsConfig.InquillagerRaidCount.get())});
            }
            if (MobsConfig.EnviokerRaid.get()) {
                builder.addEnum("GOETY_ENVIOKER", () -> new Object[]{ModEntityType.ENVIOKER, getWaves(MobsConfig.EnviokerRaidCount.get())});
            }
            if (MobsConfig.SorcererRaid.get()) {
                builder.addEnum("GOETY_SORCERER", () -> new Object[]{ModEntityType.SORCERER, getWaves(MobsConfig.SorcererRaidCount.get())});
            }
            if (MobsConfig.HostileRedstoneGolemRaid.get()) {
                builder.addEnum("GOETY_HOSTILE_RED_GOLEM", () -> new Object[]{ModEntityType.HOSTILE_REDSTONE_GOLEM, getWaves(MobsConfig.HostileRedstoneGolemRaidCount.get())});
            }
            if (MobsConfig.HostileRedstoneMonstrosityRaid.get()) {
                final AtomicReference<EntityType<? extends Raider>> entityType = new AtomicReference<>(ModEntityType.HOSTILE_REDSTONE_MONSTROSITY);
                if (MobsConfig.HRMSpawnNoRaiders.get()) {
                    entityType.set(ModEntityType.RAID_BOSS_SUMMON);
                }
                builder.addEnum("GOETY_HOSTILE_RED_MONSTER", () -> new Object[]{entityType.get(), getWaves(MobsConfig.HostileRedstoneMonstrosityRaidCount.get())});
            }
            if (MobsConfig.MinisterRaid.get()) {
                builder.addEnum("GOETY_MINISTER", () -> new Object[]{ModEntityType.MINISTER, getWaves(MobsConfig.MinisterRaidCount.get())});
            }
        }
        builder.build();
    }

    private static int[] getWaves(List<? extends Integer> list) {
        return new int[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7)};
    }

//    public static final List<Raid.RaiderType> NEW_RAID_MEMBERS = new ArrayList<>();
//
//    public static void addRaiders(){
//        if (MobsConfig.WarlockRaid.get()) {
//            addWaves("GOETY_WARLOCK", ModEntityType.WARLOCK, MobsConfig.WarlockRaidCount.get());
//        }
//        if (MobsConfig.MaverickRaid.get()) {
//            addWaves("GOETY_MAVERICK", ModEntityType.MAVERICK, MobsConfig.MaverickRaidCount.get());
//        }
//        if (MobsConfig.HereticRaid.get()) {
//            addWaves("GOETY_HERETIC", ModEntityType.HERETIC, MobsConfig.HereticRaidCount.get());
//        }
//        if (MobsConfig.IllagerRaid.get()) {
//            if (MobsConfig.PikerRaid.get()) {
//                addWaves("GOETY_PIKER", ModEntityType.PIKER, MobsConfig.PikerRaidCount.get());
//            }
//            if (MobsConfig.RipperRaid.get()) {
//                addWaves("GOETY_RIPPER", ModEntityType.RIPPER, MobsConfig.RipperRaidCount.get());
//            }
//            if (MobsConfig.CrusherRaid.get()) {
//                addWaves("GOETY_CRUSHER", ModEntityType.CRUSHER, MobsConfig.CrusherRaidCount.get());
//            }
//            if (MobsConfig.StormCasterRaid.get()) {
//                addWaves("GOETY_STORM_CASTER", ModEntityType.STORM_CASTER, MobsConfig.StormCasterRaidCount.get());
//            }
//            if (MobsConfig.CryologerRaid.get()) {
//                addWaves("GOETY_CRYOLOGER", ModEntityType.CRYOLOGER, MobsConfig.CryologerRaidCount.get());
//            }
//            if (MobsConfig.PreacherRaid.get()) {
//                addWaves("GOETY_PREACHER", ModEntityType.PREACHER, MobsConfig.PreacherRaidCount.get());
//            }
//            if (MobsConfig.ConquillagerRaid.get()) {
//                addWaves("GOETY_CONQUILLAGER", ModEntityType.CONQUILLAGER, MobsConfig.ConquillagerRaidCount.get());
//            }
//            if (MobsConfig.InquillagerRaid.get()) {
//                addWaves("GOETY_INQUILLAGER", ModEntityType.INQUILLAGER, MobsConfig.InquillagerRaidCount.get());
//            }
//            if (MobsConfig.EnviokerRaid.get()) {
//                addWaves("GOETY_ENVIOKER", ModEntityType.ENVIOKER, MobsConfig.EnviokerRaidCount.get());
//            }
//            if (MobsConfig.SorcererRaid.get()) {
//                addWaves("GOETY_SORCERER", ModEntityType.SORCERER, MobsConfig.SorcererRaidCount.get());
//            }
//            if (MobsConfig.HostileRedstoneGolemRaid.get()){
//                addWaves("GOETY_HOSTILE_RED_GOLEM", ModEntityType.HOSTILE_REDSTONE_GOLEM, MobsConfig.HostileRedstoneGolemRaidCount.get());
//            }
//            if (MobsConfig.HostileRedstoneMonstrosityRaid.get()){
//                EntityType<? extends Raider> entityType = ModEntityType.HOSTILE_REDSTONE_MONSTROSITY;
//                if (MobsConfig.HRMSpawnNoRaiders.get()) {
//                    entityType = ModEntityType.RAID_BOSS_SUMMON;
//                }
//                addWaves("GOETY_HOSTILE_RED_MONSTER", entityType, MobsConfig.HostileRedstoneMonstrosityRaidCount.get());
//            }
//            if (MobsConfig.MinisterRaid.get()) {
//                addWaves("GOETY_MINISTER", ModEntityType.MINISTER, MobsConfig.MinisterRaidCount.get());
//            }
//        }
//    }
//
//    private static Raid.RaiderType addWaves(String name, EntityType<? extends Raider> type, List<? extends Integer> list) {
//        Raid.RaiderType member = Raid.RaiderType.create(name, type, new int[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7)});
//        NEW_RAID_MEMBERS.add(member);
//        return member;
//    }
}
