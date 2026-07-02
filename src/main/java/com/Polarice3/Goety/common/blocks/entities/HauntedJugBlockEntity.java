package com.Polarice3.Goety.common.blocks.entities;

import cn.sh1rocu.goety.util.transfer.InfiniteFluidStorage;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.blocks.BrewCauldronBlock;
import com.Polarice3.Goety.common.blocks.HauntedJugBlock;
import com.Polarice3.Goety.compat.botania.BotaniaIntegration;
import com.Polarice3.Goety.compat.botania.BotaniaLoaded;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.BrewUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HauntedJugBlockEntity extends ModBlockEntity implements SidedStorageBlockEntity {
    private final InfiniteFluidStorage fluidTank = new InfiniteFluidStorage(FluidVariant.of(Fluids.WATER));

    public int search = 0;

    public HauntedJugBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.HAUNTED_JUG, p_155229_, p_155230_);
    }

    public void tick() {
        if (this.level != null) {
            if (!this.level.isClientSide) {
                if (this.getBlockState().getValue(HauntedJugBlock.ENABLED)) {
                    FluidVariant waterVariant = FluidVariant.of(Fluids.WATER);
                    if (BlockFinder.isPassableBlock(this.level, this.getBlockPos().above())) {
                        ++this.search;
                        int radius = 5;
                        int x = this.search / radius % radius;
                        int y = this.search / radius / radius % (radius - 2);
                        int z = this.search % radius;
                        if (this.search > 1 && x == 0 && y == 0 && z == 0) {
                            this.search = 0;
                        }
                        BlockPos blockPos = this.getBlockPos().offset(x - 2, y - 1, z - 2);
                        BlockState blockState = this.level.getBlockState(blockPos);
                        BlockEntity blockEntity = this.level.getBlockEntity(blockPos);
                        var fluidStorage = FluidStorage.SIDED.find(this.level, blockPos, blockState, blockEntity, Direction.UP);
                        boolean fluidHandler0 = blockEntity != null && !(blockEntity instanceof HauntedJugBlockEntity) && fluidStorage != null && !blockEntity.getBlockState().getBlock().getDescriptionId().contains("pipe");
                        boolean water = blockState.getBlock() == Blocks.WATER_CAULDRON && blockState.getValue(LayeredCauldronBlock.LEVEL) < 3;
                        boolean vanillaCauldron = blockState.getBlock() == Blocks.CAULDRON || water;
                        boolean brewCauldron = blockState.getBlock() instanceof BrewCauldronBlock && blockEntity instanceof BrewCauldronBlockEntity cauldronEntity && blockState.getValue(BrewCauldronBlock.LEVEL) < 3 && BrewUtils.isEmpty(cauldronEntity.getBrew()) && cauldronEntity.mode != BrewCauldronBlockEntity.Mode.CRAFTED;
                        AABB aabb = new AABB(blockPos);
                        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb, livingEntity -> livingEntity.isAlive() && (livingEntity.getRemainingFireTicks() > 0 || livingEntity instanceof Axolotl));
                        if (!list.isEmpty()) {
                            LivingEntity livingEntity = list.get(this.level.random.nextInt(list.size()));
                            this.streamWater(blockPos);
                            livingEntity.extinguishFire();
                            if (livingEntity.isSensitiveToWater()) {
                                livingEntity.hurt(livingEntity.damageSources().drown(), 1.0F);
                            }
                            if (livingEntity instanceof Axolotl axolotl) {
                                axolotl.rehydrate();
                            }
                        } else if (fluidHandler0) {
                            try (Transaction tx = Transaction.openOuter()) {
                                long filled = fluidStorage.insert(waterVariant, 250 * 81, tx);
                                if (filled > 0) {
                                    this.streamWater(blockPos);
                                    this.fluidTank.extract(waterVariant, filled, tx);
                                    this.markUpdated();
                                    tx.commit();
                                }
                            }
                        } else if (brewCauldron) {
                            this.level.setBlock(blockPos, blockState.cycle(BrewCauldronBlock.LEVEL), 2);
                            this.level.updateNeighborsAt(blockPos, blockState.getBlock());
                            this.streamWater(blockPos);
                            try (Transaction tx = Transaction.openOuter()) {
                                this.fluidTank.extract(waterVariant, 333 * 81, tx);
                                tx.commit();
                            }
                            this.markUpdated();
                        } else if (vanillaCauldron) {
                            if (water) {
                                this.level.setBlock(blockPos, blockState.cycle(LayeredCauldronBlock.LEVEL), 2);
                            } else if (blockState.getBlock() == Blocks.CAULDRON) {
                                this.level.setBlock(blockPos, Blocks.WATER_CAULDRON.defaultBlockState(), 2);
                            }
                            this.level.updateNeighborsAt(blockPos, blockState.getBlock());
                            this.streamWater(blockPos);
                            try (Transaction tx = Transaction.openOuter()) {
                                this.fluidTank.extract(waterVariant, 333 * 81, tx);
                                tx.commit();
                            }
                            this.markUpdated();
                        } else if (BotaniaLoaded.BOTANIA.isLoaded()) {
                            if (BotaniaIntegration.fillApothecary(blockPos, this.level)) {
                                this.level.updateNeighborsAt(blockPos, blockState.getBlock());
                                this.streamWater(blockPos);
                                try (Transaction tx = Transaction.openOuter()) {
                                    this.fluidTank.extract(waterVariant, FluidConstants.BUCKET, tx);
                                    tx.commit();
                                }
                                this.markUpdated();
                            }
                        }
                    } else {
                        BlockEntity blockEntity = this.level.getBlockEntity(this.getBlockPos().above());
                        if (blockEntity != null) {
                            var storage = FluidStorage.SIDED.find(this.level, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, Direction.DOWN);
                            if (storage != null) {
                                try (Transaction tx = Transaction.openOuter()) {
                                    long filled = storage.insert(waterVariant, FluidConstants.BUCKET, tx);
                                    if (filled > 0) {
                                        this.fluidTank.extract(waterVariant, filled, tx);
                                        this.markUpdated();
                                        tx.commit();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void streamWater(BlockPos target) {
        if (this.level != null) {
            this.level.playSound(null, this.getBlockPos(), SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.33F, 1.0F);
            if (this.level instanceof ServerLevel serverLevel) {
                Vec3 vec3 = Vec3.atBottomCenterOf(this.getBlockPos().above());
                Vec3 vec31 = Vec3.atBottomCenterOf(target);
                serverLevel.sendParticles(ModParticleTypes.WATER_STREAM, vec3.x, vec3.y, vec3.z, 0, vec31.x, vec31.y, vec31.z, 1.0F);
            }
            this.level.playSound(null, target, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 0.33F, 1.0F);
        }
    }

    @Override
    public void readNetwork(CompoundTag compoundNBT) {
        this.fluidTank.readNbt(compoundNBT);
    }

    @Override
    public CompoundTag writeNetwork(CompoundTag pCompound) {
        this.fluidTank.writeNbt(pCompound);
        return pCompound;
    }

    @Override
    public Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        return this.fluidTank;
    }
}
