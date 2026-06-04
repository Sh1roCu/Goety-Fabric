package com.Polarice3.Goety.common.blocks.entities;

import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.blocks.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final BlockEntityType<ArcaBlockEntity> ARCA = register("arca",
            () -> BlockEntityType.Builder.of(ArcaBlockEntity::new, ModBlocks.ARCA_BLOCK).build(null));

    public static final BlockEntityType<CursedInfuserBlockEntity> CURSED_INFUSER = register("cursed_infuser",
            () -> BlockEntityType.Builder.of(CursedInfuserBlockEntity::new, ModBlocks.CURSED_INFUSER).build(null));

    public static final BlockEntityType<GrimInfuserBlockEntity> GRIM_INFUSER = register("grim_infuser",
            () -> BlockEntityType.Builder.of(GrimInfuserBlockEntity::new, ModBlocks.GRIM_INFUSER).build(null));

    public static final BlockEntityType<CursedCageBlockEntity> CURSED_CAGE = register("cursed_cage",
            () -> BlockEntityType.Builder.of(CursedCageBlockEntity::new, ModBlocks.CURSED_CAGE_BLOCK).build(null));

    public static final BlockEntityType<DarkAltarBlockEntity> DARK_ALTAR = register("dark_altar",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof DarkAltarBlock)
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(DarkAltarBlockEntity::new, blocks).build(null);
            }
    );

    public static final BlockEntityType<PedestalBlockEntity> PEDESTAL = register("pedestal",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof PedestalBlock)
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(PedestalBlockEntity::new, blocks).build(null);
            }
    );

    public static final BlockEntityType<SoulAbsorberBlockEntity> SOUL_ABSORBER = register("soul_absorber",
            () -> BlockEntityType.Builder.of(SoulAbsorberBlockEntity::new, ModBlocks.SOUL_ABSORBER).build(null));

    public static final BlockEntityType<SoulMenderBlockEntity> SOUL_MENDER = register("soul_mender",
            () -> BlockEntityType.Builder.of(SoulMenderBlockEntity::new, ModBlocks.SOUL_MENDER).build(null));

    public static final BlockEntityType<IceBouquetTrapBlockEntity> ICE_BOUQUET_TRAP = register("ice_bouquet_trap",
            () -> BlockEntityType.Builder.of(IceBouquetTrapBlockEntity::new, ModBlocks.ICE_BOUQUET_TRAP).build(null));

    public static final BlockEntityType<WindBlowerBlockEntity> WIND_BLOWER = register("wind_blower",
            () -> BlockEntityType.Builder.of(WindBlowerBlockEntity::new, ModBlocks.WIND_BLOWER, ModBlocks.MARBLE_WIND_BLOWER).build(null));

    public static final BlockEntityType<ResonanceCrystalBlockEntity> RESONANCE_CRYSTAL = register("resonance_crystal",
            () -> BlockEntityType.Builder.of(ResonanceCrystalBlockEntity::new, ModBlocks.RESONANCE_CRYSTAL).build(null));

    public static final BlockEntityType<SculkDevourerBlockEntity> SCULK_DEVOURER = register("sculk_devourer",
            () -> BlockEntityType.Builder.of(SculkDevourerBlockEntity::new, ModBlocks.SCULK_DEVOURER).build(null));

    public static final BlockEntityType<SculkConverterBlockEntity> SCULK_CONVERTER = register("sculk_converter",
            () -> BlockEntityType.Builder.of(SculkConverterBlockEntity::new, ModBlocks.SCULK_CONVERTER).build(null));

    public static final BlockEntityType<SculkGrowerBlockEntity> SCULK_GROWER = register("sculk_grower",
            () -> BlockEntityType.Builder.of(SculkGrowerBlockEntity::new, ModBlocks.SCULK_GROWER).build(null));

    public static final BlockEntityType<ForbiddenGrassBlockEntity> FORBIDDEN_GRASS = register("forbidden_grass",
            () -> BlockEntityType.Builder.of(ForbiddenGrassBlockEntity::new, ModBlocks.FORBIDDEN_GRASS).build(null));

    public static final BlockEntityType<HookBellBlockEntity> HOOK_BELL = register("hook_bell",
            () -> BlockEntityType.Builder.of(HookBellBlockEntity::new, ModBlocks.HOOK_BELL).build(null));

    public static final BlockEntityType<ShriekObeliskBlockEntity> SHRIEKING_OBELISK = register("shriek_obelisk",
            () -> BlockEntityType.Builder.of(ShriekObeliskBlockEntity::new, ModBlocks.SHRIEKING_OBELISK).build(null));

    public static final BlockEntityType<NecroBrazierBlockEntity> NECRO_BRAZIER = register("necro_brazier",
            () -> BlockEntityType.Builder.of(NecroBrazierBlockEntity::new, ModBlocks.NECRO_BRAZIER).build(null));

    public static final BlockEntityType<AnimatorBlockEntity> ANIMATOR = register("animator",
            () -> BlockEntityType.Builder.of(AnimatorBlockEntity::new, ModBlocks.ANIMATOR).build(null));

    public static final BlockEntityType<BlackCrystalBlockEntity> BLACK_CRYSTAL = register("black_crystal",
            () -> BlockEntityType.Builder.of(BlackCrystalBlockEntity::new, ModBlocks.BLACK_CRYSTAL).build(null));

    public static final BlockEntityType<SoulCandlestickBlockEntity> SOUL_CANDLESTICK = register("soul_candlestick",
            () -> BlockEntityType.Builder.of(SoulCandlestickBlockEntity::new, ModBlocks.SOUL_CANDLESTICK).build(null));

    public static final BlockEntityType<NecroticCandlestickBlockEntity> NECROTIC_CANDLESTICK = register("necrotic_candlestick",
            () -> BlockEntityType.Builder.of(NecroticCandlestickBlockEntity::new, ModBlocks.NECROTIC_GOLD_CANDLESTICK, ModBlocks.WALL_NECROTIC_GOLD_CANDLESTICK).build(null));

    public static final BlockEntityType<BrewCauldronBlockEntity> BREWING_CAULDRON = register("witch_cauldron",
            () -> BlockEntityType.Builder.of(BrewCauldronBlockEntity::new, ModBlocks.BREWING_CAULDRON).build(null));

    public static final BlockEntityType<HauntedMirrorBlockEntity> HAUNTED_MIRROR = register("haunted_mirror",
            () -> BlockEntityType.Builder.of(HauntedMirrorBlockEntity::new, ModBlocks.HAUNTED_MIRROR).build(null));

    public static final BlockEntityType<HauntedJugBlockEntity> HAUNTED_JUG = register("haunted_jug",
            () -> BlockEntityType.Builder.of(HauntedJugBlockEntity::new, ModBlocks.HAUNTED_JUG).build(null));

    public static final BlockEntityType<SpiderNestBlockEntity> SPIDER_NEST = register("spider_nest",
            () -> BlockEntityType.Builder.of(SpiderNestBlockEntity::new, ModBlocks.SPIDER_NEST).build(null));

    public static final BlockEntityType<GravestoneBlockEntity> SHADE_GRAVESTONE = register("shade_gravestone",
            () -> BlockEntityType.Builder.of(GravestoneBlockEntity::new, ModBlocks.SHADE_GRAVESTONE).build(null));

    public static final BlockEntityType<BlazingCageBlockEntity> BLAZING_CAGE = register("blazing_cage",
            () -> BlockEntityType.Builder.of(BlazingCageBlockEntity::new, ModBlocks.BLAZING_CAGE).build(null));

    public static final BlockEntityType<OminousPyreBlockEntity> OMINOUS_PYRE = register("ominous_pyre",
            () -> BlockEntityType.Builder.of(OminousPyreBlockEntity::new, ModBlocks.OMINOUS_PYRE).build(null));

    public static final BlockEntityType<OminousIdolBlockEntity> OMINOUS_IDOL = register("ominous_idol",
            () -> BlockEntityType.Builder.of(OminousIdolBlockEntity::new, ModBlocks.OMINOUS_IDOL).build(null));

    public static final BlockEntityType<PithosBlockEntity> PITHOS = register("pithos",
            () -> BlockEntityType.Builder.of(PithosBlockEntity::new, ModBlocks.PITHOS).build(null));

    public static final BlockEntityType<SpiderMotherDenBlockEntity> SPIDER_MOTHER_DEN = register("spider_mother_den",
            () -> BlockEntityType.Builder.of(SpiderMotherDenBlockEntity::new, ModBlocks.SPIDER_MOTHER_DEN).build(null));

    public static final BlockEntityType<VoidSpawnerBlockEntity> VOID_SPAWNER = register("void_spawner",
            () -> BlockEntityType.Builder.of(VoidSpawnerBlockEntity::new, ModBlocks.VOID_SPAWNER).build(null));

    public static final BlockEntityType<VoidVaultBlockEntity> VOID_VAULT = register("void_vault",
            () -> BlockEntityType.Builder.of(VoidVaultBlockEntity::new, ModBlocks.VOID_VAULT).build(null));

    public static final BlockEntityType<VoidFrameBlockEntity> VOID_FRAME = register("void_frame",
            () -> BlockEntityType.Builder.of(VoidFrameBlockEntity::new, ModBlocks.VOID_FRAME).build(null));

    public static final BlockEntityType<VoidShrineBlockEntity> VOID_SHRINE = register("void_shrine",
            () -> BlockEntityType.Builder.of(VoidShrineBlockEntity::new, ModBlocks.VOID_SHRINE).build(null));

    public static final BlockEntityType<UrnBlockEntity> URN = register("crypt_urn",
            () -> BlockEntityType.Builder.of(UrnBlockEntity::new, ModBlocks.CRYPT_URN, ModBlocks.STASH_URN).build(null));

    public static final BlockEntityType<SpiderSacBlockEntity> SPIDER_SAC = register("spider_sac",
            () -> BlockEntityType.Builder.of(SpiderSacBlockEntity::new, ModBlocks.SPIDER_SAC).build(null));

    public static final BlockEntityType<HoleBlockEntity> HOLE = register("hole",
            () -> BlockEntityType.Builder.of(HoleBlockEntity::new, ModBlocks.HOLE).build(null));

    public static final BlockEntityType<PartLiquidBlockEntity> PART_LIQUID = register("part_liquid",
            () -> BlockEntityType.Builder.of(PartLiquidBlockEntity::new, ModBlocks.PART_LIQUID).build(null));

    public static final BlockEntityType<NightBeaconBlockEntity> NIGHT_BEACON = register("night_beacon",
            () -> BlockEntityType.Builder.of(NightBeaconBlockEntity::new, ModBlocks.NIGHT_BEACON).build(null));

    public static final BlockEntityType<VoidBarrelBlockEntity> VOID_BARREL = register("void_barrel",
            () -> BlockEntityType.Builder.of(VoidBarrelBlockEntity::new, ModBlocks.VOID_BARREL).build(null));

    public static final BlockEntityType<MagicLightBlockEntity> MAGIC_LIGHT = register("magic_light",
            () -> BlockEntityType.Builder.of(MagicLightBlockEntity::new, ModBlocks.SOUL_LIGHT_BLOCK, ModBlocks.GLOW_LIGHT_BLOCK).build(null));

    public static final BlockEntityType<MandalaBlockEntity> MANDALA = register("mandala",
            () -> BlockEntityType.Builder.of(MandalaBlockEntity::new, ModBlocks.MANDALA).build(null));

    public static final BlockEntityType<OminousStatueBlockEntity> OMINOUS_STATUE = register("ominous_statue",
            () -> BlockEntityType.Builder.of(OminousStatueBlockEntity::new, ModBlocks.OMINOUS_STATUE).build(null));

    public static final BlockEntityType<OminousBrazierStatueBlockEntity> OMINOUS_BRAZIER_STATUE = register("ominous_brazier_statue",
            () -> BlockEntityType.Builder.of(OminousBrazierStatueBlockEntity::new, ModBlocks.OMINOUS_BRAZIER_STATUE).build(null));

    public static final BlockEntityType<ThroneBlockEntity> THRONE = register("throne",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof ThroneBlock)
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(ThroneBlockEntity::new, blocks).build(null);
            }
    );

    public static final BlockEntityType<TallSkullBlockEntity> TALL_SKULL = register("tall_skull",
            () -> BlockEntityType.Builder.of(TallSkullBlockEntity::new, ModBlocks.TALL_SKULL_BLOCK, ModBlocks.WALL_TALL_SKULL_BLOCK).build(null));

    public static final BlockEntityType<RedstoneGolemSkullBlockEntity> REDSTONE_GOLEM_SKULL = register("redstone_golem_skull",
            () -> BlockEntityType.Builder.of(RedstoneGolemSkullBlockEntity::new, ModBlocks.REDSTONE_GOLEM_SKULL_BLOCK, ModBlocks.WALL_REDSTONE_GOLEM_SKULL_BLOCK).build(null));

    public static final BlockEntityType<GraveGolemSkullBlockEntity> GRAVE_GOLEM_SKULL = register("grave_golem_skull",
            () -> BlockEntityType.Builder.of(GraveGolemSkullBlockEntity::new, ModBlocks.GRAVE_GOLEM_SKULL_BLOCK, ModBlocks.WALL_GRAVE_GOLEM_SKULL_BLOCK).build(null));

    public static final BlockEntityType<RedstoneMonstrosityHeadBlockEntity> REDSTONE_MONSTROSITY_HEAD = register("redstone_monstrosity_head",
            () -> BlockEntityType.Builder.of(RedstoneMonstrosityHeadBlockEntity::new, ModBlocks.REDSTONE_MONSTROSITY_HEAD_BLOCK, ModBlocks.WALL_REDSTONE_MONSTROSITY_HEAD_BLOCK).build(null));

    public static final BlockEntityType<ModChestBlockEntity> MOD_CHEST = register("chest",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof ModChestBlock
                                && !(block instanceof ModTrappedChestBlock)
                                && !(block instanceof CryptChestBlock)
                                && !(block instanceof LoftyChestBlock))
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(ModChestBlockEntity::new, blocks).build(null);
            }
    );

    public static final BlockEntityType<ModTrappedChestBlockEntity> MOD_TRAPPED_CHEST = register("trapped_chest",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof ModTrappedChestBlock)
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(ModTrappedChestBlockEntity::new, blocks).build(null);
            }
    );

    public static final BlockEntityType<CryptChestBlockEntity> CRYPT_CHEST = register("crypt_chest",
            () -> BlockEntityType.Builder.of(CryptChestBlockEntity::new,
                    ModBlocks.CRYPT_CHEST).build(null));

    public static final BlockEntityType<LoftyChestBlockEntity> LOFTY_CHEST = register("lofty_chest",
            () -> BlockEntityType.Builder.of(LoftyChestBlockEntity::new,
                    ModBlocks.LOFTY_CHEST).build(null));

    public static final BlockEntityType<ModSignBlockEntity> SIGN_BLOCK_ENTITIES = register("sign",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof ModStandSignBlock || block instanceof ModWallSignBlock)
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(ModSignBlockEntity::new, blocks).build(null);
            }
    );

    public static final BlockEntityType<ModHangingSignBlockEntity> HANGING_SIGN_BLOCK_ENTITIES = register("hanging_sign",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof ModHangingSignBlock || block instanceof ModWallHangingSignBlock)
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, blocks).build(null);
            }
    );

    public static final BlockEntityType<PlushieBlockEntity> PLUSHIE = register("plushie",
            () -> {
                Block[] blocks = ModBlocks.BLOCKS.stream()
                        .filter(block -> block instanceof PlushieBlock)
                        .toArray(Block[]::new);
                return BlockEntityType.Builder.of(PlushieBlockEntity::new, blocks).build(null);
            }
    );

    public static void init() {

    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, Supplier<BlockEntityType<T>> supplier) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Goety.location(name), supplier.get());
    }

}
