package com.Polarice3.Goety.common.entities.vehicle;

import cn.sh1rocu.goety.api.extension.IEntityAdditionalSpawnData;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.items.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.function.IntFunction;

public class ModBoat extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoat.class, EntityDataSerializers.INT);

    public ModBoat(EntityType<? extends Boat> p_i50129_1_, Level p_i50129_2_) {
        super(p_i50129_1_, p_i50129_2_);
        this.blocksBuilding = true;
    }

    public ModBoat(Level p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
        this(ModEntityType.MOD_BOAT, p_i1705_1_);
        this.setPos(p_i1705_2_, p_i1705_4_, p_i1705_6_);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = p_i1705_2_;
        this.yo = p_i1705_4_;
        this.zo = p_i1705_6_;
    }

    public void setVariant(Type p_38333_) {
        this.entityData.set(DATA_ID_TYPE, p_38333_.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_38359_) {
        p_38359_.putString("Type", this.getModVariant().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_38338_) {
        if (p_38338_.contains("Type", 8)) {
            this.setVariant(Type.byName(p_38338_.getString("Type")));
        }

    }

    @Override
    public Item getDropItem() {
        return switch (this.getModBoatType()) {
            case HAUNTED -> ModItems.HAUNTED_BOAT;
            case ROTTEN -> ModItems.ROTTEN_BOAT;
            case WINDSWEPT -> ModItems.WINDSWEPT_BOAT;
            case PINE -> ModItems.PINE_BOAT;
            case CHORUS -> ModItems.CHORUS_BOAT;
            case CORRUPT_CHORUS -> ModItems.CORRUPT_CHORUS_BOAT;
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, Type.HAUNTED.ordinal());
    }

    public void setType(Type pBoatType) {
        this.entityData.set(DATA_ID_TYPE, pBoatType.ordinal());
    }

    public Type getModBoatType() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    public enum Type implements StringRepresentable {
        HAUNTED(ModBlocks.HAUNTED_PLANKS, "haunted"),
        ROTTEN(ModBlocks.ROTTEN_PLANKS, "rotten"),
        WINDSWEPT(ModBlocks.WINDSWEPT_PLANKS, "windswept"),
        PINE(ModBlocks.PINE_PLANKS, "pine"),
        CHORUS(ModBlocks.CHORUS_PLANKS, "chorus"),
        CORRUPT_CHORUS(ModBlocks.CORRUPT_CHORUS_PLANKS, "corrupt_chorus");

        private final String name;
        private final Block planks;
        public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        Type(Block p_i48146_3_, String p_i48146_4_) {
            this.name = p_i48146_4_;
            this.planks = p_i48146_3_;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static Type byId(int p_38431_) {
            return BY_ID.apply(p_38431_);
        }

        public static Type byName(String p_38433_) {
            return CODEC.byName(p_38433_, HAUNTED);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return IEntityAdditionalSpawnData.getEntitySpawningPacket(this);
    }
}
