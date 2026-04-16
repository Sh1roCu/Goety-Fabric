package com.Polarice3.Goety;

import cn.sh1rocu.goety.util.brewing.FabricCustomBrewingRegistry;
import com.Polarice3.Goety.client.ClientProxy;
import com.Polarice3.Goety.client.inventory.container.ModContainerType;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.CommonProxy;
import com.Polarice3.Goety.common.advancements.ModCriteriaTriggers;
import com.Polarice3.Goety.common.blocks.*;
import com.Polarice3.Goety.common.blocks.entities.BrewCauldronBlockEntity;
import com.Polarice3.Goety.common.blocks.entities.ModBlockEntities;
import com.Polarice3.Goety.common.blocks.fluids.ModFluids;
import com.Polarice3.Goety.common.crafting.ModRecipeSerializer;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.*;
import com.Polarice3.Goety.common.entities.ally.ender.BlastlingServant;
import com.Polarice3.Goety.common.entities.ally.ender.SnarelingServant;
import com.Polarice3.Goety.common.entities.ally.ender.WatchlingServant;
import com.Polarice3.Goety.common.entities.ally.golem.*;
import com.Polarice3.Goety.common.entities.ally.illager.*;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.HereticServant;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.MaverickServant;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.WarlockServant;
import com.Polarice3.Goety.common.entities.ally.illager.cultist.WitchServant;
import com.Polarice3.Goety.common.entities.ally.illager.raider.*;
import com.Polarice3.Goety.common.entities.ally.spider.*;
import com.Polarice3.Goety.common.entities.ally.undead.*;
import com.Polarice3.Goety.common.entities.ally.undead.bound.*;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.*;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.*;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.boss.EnderKeeper;
import com.Polarice3.Goety.common.entities.boss.Vizier;
import com.Polarice3.Goety.common.entities.deco.HauntedArmorStand;
import com.Polarice3.Goety.common.entities.hostile.*;
import com.Polarice3.Goety.common.entities.hostile.cultists.Crone;
import com.Polarice3.Goety.common.entities.hostile.cultists.Heretic;
import com.Polarice3.Goety.common.entities.hostile.cultists.Maverick;
import com.Polarice3.Goety.common.entities.hostile.cultists.Warlock;
import com.Polarice3.Goety.common.entities.hostile.ender.Blastling;
import com.Polarice3.Goety.common.entities.hostile.ender.Endersent;
import com.Polarice3.Goety.common.entities.hostile.ender.Snareling;
import com.Polarice3.Goety.common.entities.hostile.ender.Watchling;
import com.Polarice3.Goety.common.entities.hostile.illagers.*;
import com.Polarice3.Goety.common.entities.hostile.servants.*;
import com.Polarice3.Goety.common.entities.neutral.*;
import com.Polarice3.Goety.common.entities.projectiles.*;
import com.Polarice3.Goety.common.inventory.ModSaveInventory;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.ModPotions;
import com.Polarice3.Goety.common.items.ModSpawnEggs;
import com.Polarice3.Goety.common.items.ServantSpawnEggs;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.ritual.ModRituals;
import com.Polarice3.Goety.common.world.ModMobSpawnBiomeModifier;
import com.Polarice3.Goety.common.world.ModMobSpawnStructureModifier;
import com.Polarice3.Goety.common.world.features.ModFeatures;
import com.Polarice3.Goety.common.world.features.trees.trunkplacers.ModTrunkPlacerTypes;
import com.Polarice3.Goety.common.world.placements.ModPlacementType;
import com.Polarice3.Goety.common.world.processors.ModProcessors;
import com.Polarice3.Goety.common.world.structures.ModStructureTypes;
import com.Polarice3.Goety.compat.OtherModCompat;
import com.Polarice3.Goety.config.*;
import com.Polarice3.Goety.init.*;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class Goety {
    public static final String MOD_ID = "goety";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ModProxy PROXY = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? new ClientProxy() : new CommonProxy();
    public static SidedInit SIDED_INIT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? new ClientSideInit() : new SidedInit();
    public static final Supplier<Path> CONFIGDIR = () -> FabricLoader.getInstance().getConfigDir();

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void init() {
        getOrCreateDirectory(CONFIGDIR.get().resolve("goety"), "goety");

        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, MainConfig.SPEC, "goety/goety.toml");
        MainConfig.loadConfig(MainConfig.SPEC, CONFIGDIR.get().resolve("goety/goety.toml").toString());

        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, AttributesConfig.SPEC, "goety/goety-attributes.toml");
        AttributesConfig.loadConfig(AttributesConfig.SPEC, CONFIGDIR.get().resolve("goety/goety-attributes.toml").toString());

        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, SpellConfig.SPEC, "goety/goety-spells.toml");
        SpellConfig.loadConfig(SpellConfig.SPEC, CONFIGDIR.get().resolve("goety/goety-spells.toml").toString());

        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, BrewConfig.SPEC, "goety/goety-brews.toml");
        BrewConfig.loadConfig(BrewConfig.SPEC, CONFIGDIR.get().resolve("goety/goety-brews.toml").toString());

        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, MobsConfig.SPEC, "goety/goety-mobs.toml");
        MobsConfig.loadConfig(MobsConfig.SPEC, CONFIGDIR.get().resolve("goety/goety-mobs.toml").toString());

        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, ItemConfig.SPEC, "goety/goety-items.toml");
        ItemConfig.loadConfig(ItemConfig.SPEC, CONFIGDIR.get().resolve("goety/goety-items.toml").toString());

        RaidAdditions.init();

        ModTags.init();

        ModRituals.init();

        ModItems.init();
        ModAttributes.init();
        ModBlocks.init();
        ModFluids.init();
        ModRecipeSerializer.init();
        ModSpawnEggs.init();
        ServantSpawnEggs.init();
        GoetyEffects.init();
        ModPotions.init();
        ModPaintings.init();
        ModPotPatterns.init();
        ModPotPatterns.addPatterns();
        ModBanners.init();
        ModSounds.init();
        ModCriteriaTriggers.init();
        SIDED_INIT.init();

        ModBlockEntities.init();
        ModEntityType.init();
        setupEntityAttributeCreation();
        spawnPlacementEvent();
        ModFeatures.init();
        ModTrunkPlacerTypes.init();
        ModParticleTypes.init();
        ModContainerType.init();
        ModEnchantments.init();
        ModLootModifier.init();
        ModLootInject.injectLootTables();
        ModStructureTypes.init();
        ModPlacementType.init();
        ModProcessors.init();
        ModCreativeTab.init();

        commonSetup();
        finalLoad();

        ModMobSpawnBiomeModifier.init();
        ModMobSpawnStructureModifier.init();

        InitEvents.init();
    }

    public static Path getOrCreateDirectory(Path dirPath, String dirLabel) {
        if (!Files.isDirectory(dirPath.getParent())) {
            getOrCreateDirectory(dirPath.getParent(), "parent of " + dirLabel);
        }
        if (!Files.isDirectory(dirPath)) {
            LOGGER.debug("Making {} directory : {}", dirLabel, dirPath);
            try {
                Files.createDirectory(dirPath);
            } catch (IOException e) {
                if (e instanceof FileAlreadyExistsException) {
                    LOGGER.error("Failed to create {} directory - there is a file in the way", dirLabel);
                } else {
                    LOGGER.error("Problem with creating {} directory (Permissions?)", dirLabel, e);
                }
                throw new RuntimeException("Problem creating directory", e);
            }
            LOGGER.debug("Created {} directory : {}", dirLabel, dirPath);
        } else {
            LOGGER.debug("Found existing {} directory : {}", dirLabel, dirPath);
        }
        return dirPath;
    }

    private static void commonSetup() {
        OtherModCompat.setup();

        ModNetwork.registerC2SPackets();

        ModCauldronInteraction.init();

        DispenserBlock.registerBehavior(ModBlocks.TALL_SKULL_ITEM, new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                this.setSuccess(ArmorItem.dispenseArmor(source, stack));
                return stack;
            }
        });
        DispenserBlock.registerBehavior(ModItems.OMINOUS_SADDLE, new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                boolean flag = false;
                BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                List<LivingEntity> list = source.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS);
                if (!list.isEmpty()) {
                    LivingEntity livingentity = list.get(0);
                    if (livingentity instanceof ModRavager ravager) {
                        if (!ravager.hasSaddle()) {
                            ravager.equipSaddle(true);
                            flag = true;
                        }
                    }
                }
                this.setSuccess(flag);
                return stack;
            }
        });
        DispenserBlock.registerBehavior(ModItems.ILL_BOMB, new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return new IllBomb(p_123469_.x(), p_123469_.y(), p_123469_.z(), p_123468_);
            }
        });
        DispenserBlock.registerBehavior(ModItems.SNAP_FUNGUS, new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return new SnapFungus(p_123469_.x(), p_123469_.y(), p_123469_.z(), p_123468_);
            }
        });
        DispenserBlock.registerBehavior(ModItems.BLAST_FUNGUS, new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return new BlastFungus(p_123469_.x(), p_123469_.y(), p_123469_.z(), p_123468_);
            }
        });
        DispenserBlock.registerBehavior(ModItems.BERSERK_FUNGUS, new AbstractProjectileDispenseBehavior() {
            @Override
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return new BerserkFungus(p_123469_.x(), p_123469_.y(), p_123469_.z(), p_123468_);
            }
        });
        DispenserBlock.registerBehavior(ModItems.SPLASH_BREW, new DispenseItemBehavior() {
            @Override
            public ItemStack dispense(BlockSource p_123491_, ItemStack p_123492_) {
                return (new AbstractProjectileDispenseBehavior() {
                    protected Projectile getProjectile(Level p_123501_, Position p_123502_, ItemStack p_123503_) {
                        return Util.make(new ThrownBrew(p_123501_, p_123502_.x(), p_123502_.y(), p_123502_.z()), (p_123499_) -> {
                            p_123499_.setItem(p_123503_);
                        });
                    }

                    protected float getUncertainty() {
                        return super.getUncertainty() * 0.5F;
                    }

                    protected float getPower() {
                        return super.getPower() * 1.25F;
                    }
                }).dispense(p_123491_, p_123492_);
            }
        });
        DispenserBlock.registerBehavior(ModItems.LINGERING_BREW, new DispenseItemBehavior() {
            @Override
            public ItemStack dispense(BlockSource p_123507_, ItemStack p_123508_) {
                return (new AbstractProjectileDispenseBehavior() {
                    protected Projectile getProjectile(Level p_123517_, Position p_123518_, ItemStack p_123519_) {
                        return Util.make(new ThrownBrew(p_123517_, p_123518_.x(), p_123518_.y(), p_123518_.z()), (p_123515_) -> {
                            p_123515_.setItem(p_123519_);
                        });
                    }

                    protected float getUncertainty() {
                        return super.getUncertainty() * 0.5F;
                    }

                    protected float getPower() {
                        return super.getPower() * 1.25F;
                    }
                }).dispense(p_123507_, p_123508_);
            }
        });
        DispenserBlock.registerBehavior(ModItems.GAS_BREW, new DispenseItemBehavior() {
            @Override
            public ItemStack dispense(BlockSource p_123507_, ItemStack p_123508_) {
                return (new AbstractProjectileDispenseBehavior() {
                    protected Projectile getProjectile(Level p_123517_, Position p_123518_, ItemStack p_123519_) {
                        return Util.make(new ThrownBrew(p_123517_, p_123518_.x(), p_123518_.y(), p_123518_.z()), (p_123515_) -> {
                            p_123515_.setItem(p_123519_);
                        });
                    }

                    protected float getUncertainty() {
                        return super.getUncertainty() * 0.5F;
                    }

                    protected float getPower() {
                        return super.getPower() * 1.25F;
                    }
                }).dispense(p_123507_, p_123508_);
            }
        });
        DispenserBlock.registerBehavior(ModItems.HAUNTED_ARMOR_STAND, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource p_123461_, ItemStack p_123462_) {
                Direction direction = p_123461_.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = p_123461_.getPos().relative(direction);
                Level level = p_123461_.getLevel();
                HauntedArmorStand armorstand = new HauntedArmorStand(level, (double) blockpos.getX() + 0.5D, blockpos.getY(), (double) blockpos.getZ() + 0.5D);
                EntityType.updateCustomEntityTag(level, null, armorstand, p_123462_.getTag());
                armorstand.setYRot(direction.toYRot());
                level.addFreshEntity(armorstand);
                p_123462_.shrink(1);
                return p_123462_;
            }
        });
        DispenserBlock.registerBehavior(ModItems.QUICK_GROWING_SEED, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource p_123461_, ItemStack p_123462_) {
                Direction direction = p_123461_.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = p_123461_.getPos().relative(direction);
                Level level = p_123461_.getLevel();
                AbstractVine vine = ModEntityType.QUICK_GROWING_VINE.create(level);
                if (vine != null) {
                    EntityType<?> entityType = vine.getVariant(null, level, blockpos);
                    if (entityType != null) {
                        vine = (AbstractVine) entityType.create(level);
                    }
                    if (vine != null) {
                        Vec3 vec3 = new Vec3((double) blockpos.getX() + 0.5D, blockpos.getY(), (double) blockpos.getZ() + 0.5D);
                        vine.setPos(vec3);
                        if (level instanceof ServerLevel serverLevel) {
                            vine.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                        }
                        vine.setPerpetual(true);
                        if (level.addFreshEntity(vine)) {
                            p_123462_.shrink(1);
                        }
                    }
                }
                return p_123462_;
            }
        });
        DispenserBlock.registerBehavior(ModItems.POISON_QUILL_SEED, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource p_123461_, ItemStack p_123462_) {
                Direction direction = p_123461_.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = p_123461_.getPos().relative(direction);
                Level level = p_123461_.getLevel();
                AbstractVine vine = ModEntityType.POISON_QUILL_VINE.create(level);
                if (vine != null) {
                    EntityType<?> entityType = vine.getVariant(null, level, blockpos);
                    if (entityType != null) {
                        vine = (AbstractVine) entityType.create(level);
                    }
                    if (vine != null) {
                        Vec3 vec3 = new Vec3((double) blockpos.getX() + 0.5D, blockpos.getY(), (double) blockpos.getZ() + 0.5D);
                        vine.setPos(vec3);
                        if (level instanceof ServerLevel serverLevel) {
                            vine.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                        }
                        vine.setPerpetual(true);
                        if (level.addFreshEntity(vine)) {
                            p_123462_.shrink(1);
                        }
                    }
                }
                return p_123462_;
            }
        });
        DispenserBlock.registerBehavior(ModItems.VOID_BUCKET, new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack execute(BlockSource p_123561_, ItemStack p_123562_) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem) p_123562_.getItem();
                BlockPos blockpos = p_123561_.getPos().relative(p_123561_.getBlockState().getValue(DispenserBlock.FACING));
                Level level = p_123561_.getLevel();
                if (dispensiblecontaineritem.emptyContents(null, level, blockpos, null)) {
                    dispensiblecontaineritem.checkExtraContent(null, level, p_123562_, blockpos);
                    return new ItemStack(Items.BUCKET);
                } else {
                    return this.defaultDispenseItemBehavior.dispense(p_123561_, p_123562_);
                }
            }
        });
        ModDispenserRegister.registerAlternativeDispenseBehavior(new ModDispenserRegister.AlternativeDispenseBehavior(
                Goety.MOD_ID, Items.WATER_BUCKET,
                (blockSource, itemStack) -> blockSource.getLevel().getBlockState(ModDispenserRegister.offsetPos(blockSource)).is(ModBlocks.BREWING_CAULDRON),
                new OptionalDispenseItemBehavior() {
                    @Override
                    protected ItemStack execute(BlockSource source, ItemStack stack) {
                        BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                        BlockState blockState = source.getLevel().getBlockState(blockpos);
                        if (blockState.is(ModBlocks.BREWING_CAULDRON)) {
                            if (blockState.getValue(BrewCauldronBlock.LEVEL) < 3) {
                                this.setSuccess(source.getLevel().setBlockAndUpdate(blockpos, blockState.setValue(BrewCauldronBlock.LEVEL, 3)));
                                return new ItemStack(Items.BUCKET);
                            }
                        }
                        return stack;
                    }
                }));
        ModDispenserRegister.registerAlternativeDispenseBehavior(new ModDispenserRegister.AlternativeDispenseBehavior(
                Goety.MOD_ID, Items.BUCKET,
                (blockSource, itemStack) -> blockSource.getLevel().getBlockState(ModDispenserRegister.offsetPos(blockSource)).is(ModBlocks.BREWING_CAULDRON),
                new OptionalDispenseItemBehavior() {
                    @Override
                    protected ItemStack execute(BlockSource source, ItemStack stack) {
                        BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                        BlockState blockState = source.getLevel().getBlockState(blockpos);
                        if (blockState.is(ModBlocks.BREWING_CAULDRON)) {
                            if (blockState.getValue(BrewCauldronBlock.LEVEL) == 3) {
                                if (source.getLevel().getBlockEntity(blockpos) instanceof BrewCauldronBlockEntity blockEntity) {
                                    blockEntity.reset();
                                }
                                this.setSuccess(source.getLevel().setBlockAndUpdate(blockpos, blockState.setValue(BrewCauldronBlock.LEVEL, 0)));
                                return new ItemStack(Items.WATER_BUCKET);
                            }
                        }
                        return stack;
                    }
                }));

        StrippableBlockRegistry.register(ModBlocks.HAUNTED_LOG, ModBlocks.STRIPPED_HAUNTED_LOG);
        StrippableBlockRegistry.register(ModBlocks.HAUNTED_WOOD, ModBlocks.STRIPPED_HAUNTED_WOOD);
        StrippableBlockRegistry.register(ModBlocks.ROTTEN_LOG, ModBlocks.STRIPPED_ROTTEN_LOG);
        StrippableBlockRegistry.register(ModBlocks.ROTTEN_WOOD, ModBlocks.STRIPPED_ROTTEN_WOOD);
        StrippableBlockRegistry.register(ModBlocks.WINDSWEPT_LOG, ModBlocks.STRIPPED_WINDSWEPT_LOG);
        StrippableBlockRegistry.register(ModBlocks.WINDSWEPT_WOOD, ModBlocks.STRIPPED_WINDSWEPT_WOOD);
        StrippableBlockRegistry.register(ModBlocks.PINE_LOG, ModBlocks.STRIPPED_PINE_LOG);
        StrippableBlockRegistry.register(ModBlocks.PINE_WOOD, ModBlocks.STRIPPED_PINE_WOOD);

//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SIENNA_GRASS.getId(), ModBlocks.POTTED_SIENNA_GRASS);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SIENNA_FERN.getId(), ModBlocks.POTTED_SIENNA_FERN);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WINDSWEPT_DEAD_BUSH.getId(), ModBlocks.POTTED_WINDSWEPT_DEAD_BUSH);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CHORUS_STALK.getId(), ModBlocks.POTTED_CHORUS_STALK);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CHORUS_FERN.getId(), ModBlocks.POTTED_CHORUS_FERN);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.HAUNTED_SAPLING.getId(), ModBlocks.POTTED_HAUNTED_SAPLING);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.ROTTEN_SAPLING.getId(), ModBlocks.POTTED_ROTTEN_SAPLING);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WINDSWEPT_SAPLING.getId(), ModBlocks.POTTED_WINDSWEPT_SAPLING);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PINE_SAPLING.getId(), ModBlocks.POTTED_PINE_SAPLING);
//        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CHORUS_SAPLING.getId(), ModBlocks.POTTED_CHORUS_SAPLING);

        ModWoodType.init();

        addBrewingRecipes();

        FlammableBlockRegistry flammableBlockRegistry = FlammableBlockRegistry.getDefaultInstance();

        Collection<Block> blocks = new ArrayList<>();
        ModBlocks.BLOCKS.forEach(block -> {
            if (block.defaultBlockState().ignitedByLava()) {
                blocks.add(block);
            }
        });
        for (Block block : blocks) {
            if (block.defaultBlockState().ignitedByLava()) {
                if (!(block instanceof PressurePlateBlock) && !(block instanceof AbstractChestBlock<?>)
                        && !(block instanceof DoorBlock) && !(block instanceof SignBlock)
                        && !(block instanceof SpiderNestBlock) && !(block instanceof WitchPoleBlock)) {
                    flammableBlockRegistry.add(block, 5, 20);
                }
            }
        }

        CompostingChanceRegistry.INSTANCE.add(ModBlocks.END_GROWTH_VINES.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.HAUNTED_SAPLING.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.WINDSWEPT_SAPLING.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.PINE_SAPLING.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.WINDSWEPT_LEAVES.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.PINE_LEAVES.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_VINE.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_BLOSSOM_VINES_PRUNED.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.SIENNA_GRASS.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.SIENNA_FERN.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.FIRETHORN_BERRIES, 0.3F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.TALL_SIENNA_GRASS.asItem(), 0.5F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.LARGE_SIENNA_FERN.asItem(), 0.5F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SNAP_FUNGUS, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.ROTTEN_SAPLING.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_SAPLING.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_SPROUT.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.END_GRASS_SPROUT.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_FERN_SPROUT.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.ROTTEN_LEAVES.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_LEAVES.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_BLOSSOM_LEAVES.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_STALK.asItem(), 0.85F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.END_GRASS.asItem(), 0.85F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_TALL_GRASS.asItem(), 0.85F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_FERN.asItem(), 0.85F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.LARGE_CHORUS_STALK.asItem(), 1.0F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.TALL_END_GRASS.asItem(), 1.0F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.LARGE_CHORUS_FERN.asItem(), 1.0F);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.CHORUS_BLOSSOM_VINES.asItem(), 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.RED_MOSS_GROWTH, 0.65F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHORUS_GROWTH, 1.0F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BLAST_FUNGUS, 1.0F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BERSERK_FUNGUS, 1.0F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.QUICK_GROWING_SEED, 1.0F);
        CompostingChanceRegistry.INSTANCE.add(ModItems.POISON_QUILL_SEED, 1.0F);
    }

    private static void finalLoad() {
        ModDispenserRegister.getSortedAlternativeDispenseBehaviors().forEach(ModDispenserRegister.AlternativeDispenseBehavior::register);
        ModFluids.interactionInit();
    }

    private static void addBrewingRecipes() {
        FabricCustomBrewingRegistry.addRecipe(Ingredient.of(ModItems.SNAP_FUNGUS.getDefaultInstance()), Ingredient.of(Items.LILY_OF_THE_VALLEY), new ItemStack(ModItems.BERSERK_FUNGUS));
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(ModItems.SPIDER_EGG), ModPotions.CLIMBING);
        FabricBrewingRecipeRegistry.registerPotionRecipe(ModPotions.CLIMBING, Ingredient.of(Items.REDSTONE), ModPotions.LONG_CLIMBING);
    }

    private static void setupEntityAttributeCreation() {
        FabricDefaultAttributeRegistry.register(ModEntityType.APOSTLE, Apostle.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.OBSIDIAN_MONOLITH, ObsidianMonolith.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WARLOCK, Warlock.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WARTLING, Wartling.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HERETIC, Heretic.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MAVERICK, Maverick.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CRONE, Crone.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SKELETON_VILLAGER_SERVANT, SkeletonVillagerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ZPIGLIN_SERVANT, ZPiglinServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ZPIGLIN_BRUTE_SERVANT, ZPiglinBruteServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MALGHAST, Malghast.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.INFERNO, Inferno.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.DAMNED, Damned.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VAMPIRE_BAT, VampireBat.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HOSTILE_BLACK_WOLF, HostileBlackWolf.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.FRAYED, Frayed.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.RATTLED, Rattled.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.REAPER, Reaper.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WRAITH, Wraith.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BORDER_WRAITH, BorderWraith.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MUCK_WRAITH, MuckWraith.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CRYPT_SLIME, CryptSlime.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WEB_SPIDER, WebSpider.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ICY_SPIDER, IcySpider.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BONE_SPIDER, BoneSpider.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BROOD_MOTHER, AbstractBroodMother.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.NECROMANCER, HostileNecromancer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CAIRN_NECROMANCER, CairnNecromancer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MOSSY_NECROMANCER, MossyNecromancer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HAUNTED_ARMOR, HauntedArmor.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WATCHLING, Watchling.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BLASTLING, Blastling.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SNARELING, Snareling.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ENDERSENT, Endersent.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ENDER_KEEPER, EnderKeeper.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VEX_SERVANT, AllyVex.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.IRK_SERVANT, AllyIrk.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ZOMBIE_SERVANT, ZombieServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ZOMBIE_VILLAGER_SERVANT, ZombieVillagerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HUSK_SERVANT, HuskServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.DROWNED_SERVANT, DrownedServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.FROZEN_ZOMBIE_SERVANT, FrozenZombieServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.JUNGLE_ZOMBIE_SERVANT, JungleZombieServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.FRAYED_SERVANT, FrayedServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BLACKGUARD_SERVANT, BlackguardServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SKELETON_SERVANT, SkeletonServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.STRAY_SERVANT, StrayServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WITHER_SKELETON_SERVANT, WitherSkeletonServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MOSSY_SKELETON_SERVANT, MossySkeletonServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SUNKEN_SKELETON_SERVANT, SunkenSkeletonServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.RATTLED_SERVANT, RattledServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.NECROMANCER_SERVANT, NecromancerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CAIRN_NECROMANCER_SERVANT, CairnNecromancerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MOSSY_NECROMANCER_SERVANT, MossyNecromancerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.DROWNED_NECROMANCER_SERVANT, DrownedNecromancer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WITHER_NECROMANCER_SERVANT, WitherNecromancerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.REAPER_SERVANT, ReaperServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WRAITH_SERVANT, WraithServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BORDER_WRAITH_SERVANT, BorderWraithServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MUCK_WRAITH_SERVANT, MuckWraithServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.PHANTOM_SERVANT, PhantomServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VANGUARD_SERVANT, VanguardServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SKELETON_PILLAGER_SERVANT, SkeletonPillagerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ZOMBIE_VINDICATOR_SERVANT, ZombieVindicatorServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BOUND_EVOKER, BoundEvoker.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BOUND_GEOMANCER, BoundGeomancer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BOUND_ICEOLOGER, BoundIceologer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BOUND_CRYOLOGER, BoundCryologer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BOUND_WIND_CALLER, BoundWindCaller.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BOUND_STORM_CASTER, BoundStormCaster.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HAUNTED_ARMOR_SERVANT, HauntedArmorServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HAUNTED_SKULL, HauntedSkull.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.DOPPELGANGER, Doppelganger.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MINI_GHAST, MiniGhast.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.GHAST_SERVANT, GhastServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BLAZE_SERVANT, BlazeServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WILDFIRE, Wildfire.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SLIME_SERVANT, SlimeServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MAGMA_CUBE_SERVANT, MagmaCubeServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CRYPT_SLIME_SERVANT, CryptSlimeServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.TROPICAL_SLIME_SERVANT, TropicalSlimeServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SPIDER_SERVANT, SpiderServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CAVE_SPIDER_SERVANT, CaveSpiderServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WEB_SPIDER_SERVANT, WebSpiderServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ICY_SPIDER_SERVANT, IcySpiderServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BONE_SPIDER_SERVANT, BoneSpiderServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BROOD_MOTHER_SERVANT, AbstractBroodMother.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.PRISONER, Villager.createAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.NEOLLAGER, Neollager.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.PILLAGER_SERVANT, PillagerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.PIKER_SERVANT, PikerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SIGNALER_SERVANT, SignalerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VINDICATOR_SERVANT, VindicatorServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VINDICATOR_CHEF_SERVANT, VindicatorChefServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MOUNTAINEER_SERVANT, MountaineerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CRUSHER_SERVANT, CrusherServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.EVOKER_SERVANT, EvokerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.GEOMANCER_SERVANT, GeomancerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ICEOLOGER_SERVANT, IceologerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CRYOLOGER_SERVANT, CryologerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WIND_CALLER_SERVANT, WindCallerServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.STORM_CASTER_SERVANT, StormCasterServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.RIPPER_SERVANT, RipperServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.TRAMPLER_SERVANT, AllyTrampler.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.RAVAGED, Ravaged.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MOD_RAVAGER, ModRavager.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ARMORED_RAVAGER, Ravager.createAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ZOMBIE_RAVAGER, ZombieRavager.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WITCH_SERVANT, WitchServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WARLOCK_SERVANT, WarlockServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HERETIC_SERVANT, HereticServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MAVERICK_SERVANT, MaverickServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BLACK_WOLF, BlackWolf.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SKELETON_WOLF, SkeletonWolf.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WINTER_WOLF, WinterWolf.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.STORMHOUND, Stormhound.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HELLHOUND, Hellhound.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.TWILIGHT_GOAT, TwilightGoat.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SNAPPER, Snapper.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.GNASHER, Gnasher.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.GUARDIAN_SERVANT, GuardianServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BEAR_SERVANT, BearServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.POLAR_BEAR_SERVANT, BearServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HOGLIN_SERVANT, HoglinServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BLACK_BEAST, BlackBeast.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WHISPERER, Whisperer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WAVEWHISPERER, Wavewhisperer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.LEAPLEAF, Leapleaf.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.STONE_MINISTROSITY, StoneMinistrosity.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.REDSTONE_MINISTROSITY, RedstoneMinistrosity.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ICE_GOLEM, IceGolem.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SQUALL_GOLEM, SquallGolem.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.REDSTONE_GOLEM, RedstoneGolem.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.GRAVE_GOLEM, GraveGolem.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HAUNT, Haunt.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.REDSTONE_MONSTROSITY, RedstoneMonstrosity.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.REDSTONE_CUBE, RedstoneCube.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WATCHLING_SERVANT, WatchlingServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BLASTLING_SERVANT, BlastlingServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SNARELING_SERVANT, SnarelingServant.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.TOTEMIC_WALL, TotemicWall.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.TOTEMIC_BOMB, TotemicBomb.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.GLACIAL_WALL, GlacialWall.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.QUICK_GROWING_VINE, QuickGrowingVine.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.QUICK_GROWING_KELP, QuickGrowingKelp.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.POISON_QUILL_VINE, PoisonQuillVine.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.POISON_ANEMONE, PoisonAnemone.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SPIDER_EGG, SpiderEgg.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.INSECT_SWARM, InsectSwarm.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BEAST_HEAD, BeastHead.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.GULF_TENTACLE, GulfTentacle.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VOLCANO, Volcano.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SORCERER, Sorcerer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.ENVIOKER, Envioker.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.TORMENTOR, Tormentor.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.INQUILLAGER, Inquillager.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CONQUILLAGER, Conquillager.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.PIKER, Piker.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.RIPPER, Ripper.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.TRAMPLER, Trampler.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CRUSHER, Crusher.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.STORM_CASTER, StormCaster.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CRYOLOGER, Cryologer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.PREACHER, Preacher.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.MINISTER, Minister.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HOSTILE_REDSTONE_GOLEM, HostileRedstoneGolem.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HOSTILE_REDSTONE_MONSTROSITY, HostileRedstoneMonstrosity.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VIZIER, Vizier.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.VIZIER_CLONE, VizierClone.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.IRK, Irk.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WIGHT, Wight.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CARRION_MAGGOT, CarrionMaggot.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.CARRION_FLY, CarrionFly.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SKULL_LORD, SkullLord.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.BONE_LORD, BoneLord.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.WITHER_NECROMANCER, WitherNecromancer.setCustomAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.RAID_BOSS_SUMMON, Monster.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.SURVEY_EYE, Mob.createMobAttributes().build());
        FabricDefaultAttributeRegistry.register(ModEntityType.HAUNTED_ARMOR_STAND, LivingEntity.createLivingAttributes().build());
    }

    private static void spawnPlacementEvent() {
        SpawnPlacements.register(ModEntityType.WARLOCK, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.HERETIC, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.MAVERICK, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.OBSIDIAN_MONOLITH, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ObsidianMonolith::checkOMSpawnRules);
        SpawnPlacements.register(ModEntityType.HOSTILE_BLACK_WOLF, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkDayMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.FRAYED, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frayed::checkFrayedSpawnRules);
        SpawnPlacements.register(ModEntityType.RATTLED, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Rattled::checkRattledSpawnRules);
        SpawnPlacements.register(ModEntityType.REAPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.WRAITH, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.BORDER_WRAITH, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.MUCK_WRAITH, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.CRYPT_SLIME, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CryptSlime::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.WEB_SPIDER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.ICY_SPIDER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.BONE_SPIDER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityType.NECROMANCER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.CAIRN_NECROMANCER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.MOSSY_NECROMANCER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.HAUNTED_ARMOR, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.WATCHLING, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.BLASTLING, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.SNARELING, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
        SpawnPlacements.register(ModEntityType.ENDERSENT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Owned::checkHostileSpawnRules);
    }

    public static void onServerStarting(MinecraftServer server) {
        ModSaveInventory.resetInstance();
        ModSaveInventory.setInstance(server.overworld());
    }

    public static void onServerStopped(MinecraftServer server) {
        ModSaveInventory.resetInstance();
    }
}
