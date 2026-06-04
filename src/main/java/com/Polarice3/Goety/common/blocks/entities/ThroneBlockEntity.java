package com.Polarice3.Goety.common.blocks.entities;

import com.Polarice3.Goety.api.blocks.IEnchantedBlock;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;

public class ThroneBlockEntity extends OwnedBlockEntity implements IEnchantedBlock {
    protected final Object2IntMap<Enchantment> enchantments = new Object2IntOpenHashMap<>();

    public ThroneBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.THRONE, p_155229_, p_155230_);
    }

    @Override
    public Object2IntMap<Enchantment> getEnchantments() {
        return this.enchantments;
    }

    @Override
    public void readNetwork(CompoundTag tag) {
        super.readNetwork(tag);
        this.loadEnchants(tag);
    }

    @Override
    public CompoundTag writeNetwork(CompoundTag tag) {
        this.saveEnchants(tag, this.getBlockState().getBlock().asItem());
        return super.writeNetwork(tag);
    }
}
