package com.Polarice3.Goety.common.blocks.entities;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.ZombieServant;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SPlayWorldSoundPacket;
import com.Polarice3.Goety.config.SpellConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.init.ModTags;
import com.Polarice3.Goety.utils.BlockFinder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class GravestoneBlockEntity extends TrainingBlockEntity {

    public GravestoneBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.SHADE_GRAVESTONE, p_155229_, p_155230_);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, TrainingBlockEntity blockEntity) {
        super.tick(level, blockPos, blockState, blockEntity);
        if (blockEntity.isTraining()) {
            if (blockEntity.trainTime != blockEntity.getMaxTrainTime()) {
                if (blockEntity.trainTime % 20 == 0) {
                    level.playSound(null, blockPos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            if (level instanceof ServerLevel serverLevel) {
                double d0 = (double) blockPos.getX() + level.getRandom().nextDouble();
                double d1 = (double) blockPos.getY() + level.getRandom().nextDouble();
                double d2 = (double) blockPos.getZ() + level.getRandom().nextDouble();
                serverLevel.sendParticles(ModParticleTypes.NECRO_FIRE, d0, d1, d2, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                if (level.getRandom().nextFloat() < 0.3F) {
                    if (level.getRandom().nextFloat() < 0.17F) {
                        ModNetwork.sendToALL(serverLevel.getServer(), SPlayWorldSoundPacket.ID, SPlayWorldSoundPacket.encode(blockPos, SoundEvents.FURNACE_FIRE_CRACKLE, 0.5F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F));
                    }
                }
            }
        }
    }

    @Override
    public void setVariant(ItemStack itemStack, Level level, BlockPos blockPos) {
        if (level instanceof ServerLevel serverLevel) {
            BlockState blockState0 = level.getBlockState(blockPos);
            if (blockState0.hasProperty(BlockStateProperties.WATERLOGGED) && blockState0.getValue(BlockStateProperties.WATERLOGGED)) {
                if (this.getTrainMob() != ModEntityType.DROWNED_SERVANT) {
                    this.setEntityType(ModEntityType.DROWNED_SERVANT);
                    this.markUpdated();
                }
            } else if (serverLevel.getBiome(blockPos.above()).is(ConventionalBiomeTags.DESERT) || (getBlocks(blockState -> blockState.is(BlockTags.SAND/*Tags.Blocks.SAND*/), 15) && getBlocks(blockState -> blockState.is(Blocks.GOLD_BLOCK/*Tags.Blocks.STORAGE_BLOCKS_GOLD*/), 1))) {
                if (this.getTrainMob() != ModEntityType.HUSK_SERVANT) {
                    this.setEntityType(ModEntityType.HUSK_SERVANT);
                    this.markUpdated();
                }
            } else if (serverLevel.getBiome(blockPos.above()).value().coldEnoughToSnow(blockPos.above()) || (getBlocks(blockState -> blockState.is(BlockTags.SNOW), 20) && getBlocks(blockState -> blockState.is(BlockTags.ICE), 8))) {
                if (this.getTrainMob() != ModEntityType.FROZEN_ZOMBIE_SERVANT) {
                    this.setEntityType(ModEntityType.FROZEN_ZOMBIE_SERVANT);
                    this.markUpdated();
                }
            } else if (serverLevel.getBiome(blockPos.above()).is(BiomeTags.IS_JUNGLE) || (getBlocks(blockState -> blockState.getBlock() instanceof VineBlock, 20) && getBlocks(blockState -> blockState.is(BlockTags.LEAVES), 8))) {
                if (this.getTrainMob() != ModEntityType.JUNGLE_ZOMBIE_SERVANT) {
                    this.setEntityType(ModEntityType.JUNGLE_ZOMBIE_SERVANT);
                    this.markUpdated();
                }
            } else if ((serverLevel.isThundering() && serverLevel.canSeeSky(blockPos.above()))
                    || (getBlocks(blockState -> blockState.getBlock() instanceof LightningRodBlock, 1) && getBlocks(blockState -> blockState.is(Blocks.COPPER_BLOCK/*Tags.Blocks.STORAGE_BLOCKS_COPPER*/), 8))) {
                if (this.getTrainMob() != ModEntityType.FRAYED_SERVANT) {
                    this.setEntityType(ModEntityType.FRAYED_SERVANT);
                    this.markUpdated();
                }
            } else if (BlockFinder.findStructure(serverLevel, blockPos.above(), StructureTags.ON_WOODLAND_EXPLORER_MAPS) || (getBlocks(blockState -> blockState.is(Blocks.DARK_OAK_LOG), 15) && getBlocks(blockState -> blockState.is(Blocks.ANVIL), 1))) {
                if (this.getTrainMob() != ModEntityType.ZOMBIE_VINDICATOR_SERVANT) {
                    this.setEntityType(ModEntityType.ZOMBIE_VINDICATOR_SERVANT);
                    this.markUpdated();
                }
            } else if (BlockFinder.findStructure(serverLevel, blockPos.above(), ModTags.Structures.CAN_SUMMON_BRUTES)
                    || (getBlocks(blockState -> blockState.getBlock().getDescriptionId().contains("blackstone"), 25) && getBlocks(blockState -> blockState.is(Blocks.GOLD_BLOCK/*Tags.Blocks.STORAGE_BLOCKS_GOLD*/), 8) && getBlocks(blockState -> blockState.is(Blocks.ANCIENT_DEBRIS/*Tags.Blocks.ORES_NETHERITE_SCRAP*/), 1))) {
                if (this.getTrainMob() != ModEntityType.ZPIGLIN_BRUTE_SERVANT) {
                    this.setEntityType(ModEntityType.ZPIGLIN_BRUTE_SERVANT);
                    this.markUpdated();
                }
            } else if (serverLevel.dimension() == Level.NETHER || (getBlocks(blockState -> blockState.is(Blocks.NETHERRACK/*Tags.Blocks.NETHERRACK*/), 25) && getBlocks(blockState -> blockState.getBlock() instanceof BaseFireBlock, 2))) {
                if (this.getTrainMob() != ModEntityType.ZPIGLIN_SERVANT) {
                    this.setEntityType(ModEntityType.ZPIGLIN_SERVANT);
                    this.markUpdated();
                }
            } else if ((BlockFinder.findStructure(serverLevel, blockPos.above(), StructureTags.VILLAGE) || BlockFinder.findVillageSize(serverLevel, blockPos.above(), 3)) || (getBlocks(blockState -> blockState.is(Blocks.DIRT_PATH), 14))) {
                if (this.getTrainMob() != ModEntityType.ZOMBIE_VILLAGER_SERVANT) {
                    this.setEntityType(ModEntityType.ZOMBIE_VILLAGER_SERVANT);
                    this.markUpdated();
                }
            } else {
                if (this.getTrainMob() != ModEntityType.ZOMBIE_SERVANT) {
                    this.setEntityType(ModEntityType.ZOMBIE_SERVANT);
                    this.markUpdated();
                }
            }
        }
    }

    @Override
    public void startTraining(int amount, ItemStack itemStack) {
        super.startTraining(amount, itemStack);
        if (this.level != null) {
            this.level.playSound(null, this.getBlockPos(), ModSounds.GRAVESTONE_START, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void playSpawnSound() {
        if (this.level != null) {
            this.level.playSound(null, this.getBlockPos(), ModSounds.NECROMANCER_SUMMON, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public int maxTrainAmount() {
        return 5;
    }

    @Override
    public boolean summonLimit() {
        int count = 0;
        if (this.level instanceof ServerLevel serverLevel) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof ZombieServant servant) {
                    if (this.getTrueOwner() != null && servant.getTrueOwner() == this.getTrueOwner() && servant.isAlive()) {
                        ++count;
                    }
                }
            }
        }
        return count >= SpellConfig.ZombieLimit.get();
    }

    @Override
    public boolean isFuel(ItemStack itemStack) {
        //TODO: change to ConventionalItemTags.IRON_NUGGET
        return itemStack.is(Items.IRON_NUGGET);
    }
}
