package com.Polarice3.Goety.client.render.block;

import cn.sh1rocu.goety.api.extension.IChestMaterial;
import cn.sh1rocu.goety.mixin.accessor.ChestRendererAccessor;
import com.Polarice3.Goety.Goety;
import com.Polarice3.Goety.common.blocks.ModBlocks;
import com.Polarice3.Goety.common.blocks.entities.ModChestBlockEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.EnumMap;
import java.util.Map;

/**
 * Based on @TeamTwilight's TwilightChestRenderer: <a href="https://github.com/TeamTwilight/twilightforest/blob/1.20.x/src/main/java/twilightforest/client/renderer/tileentity/TwilightChestRenderer.java">...</a>
 */
public class ModChestRenderer<T extends ModChestBlockEntity> extends ChestRenderer<T> implements IChestMaterial<T> {
    public static final Map<Block, EnumMap<ChestType, Material>> MATERIALS;

    static {
        ImmutableMap.Builder<Block, EnumMap<ChestType, Material>> builder = ImmutableMap.builder();

        builder.put(ModBlocks.HAUNTED_CHEST, chestMaterial("haunted"));
        builder.put(ModBlocks.TRAPPED_HAUNTED_CHEST, trappedMaterial("haunted"));
        builder.put(ModBlocks.ROTTEN_CHEST, chestMaterial("rotten"));
        builder.put(ModBlocks.TRAPPED_ROTTEN_CHEST, trappedMaterial("rotten"));
        builder.put(ModBlocks.WINDSWEPT_CHEST, chestMaterial("windswept"));
        builder.put(ModBlocks.TRAPPED_WINDSWEPT_CHEST, trappedMaterial("windswept"));
        builder.put(ModBlocks.PINE_CHEST, chestMaterial("pine"));
        builder.put(ModBlocks.TRAPPED_PINE_CHEST, trappedMaterial("pine"));
        builder.put(ModBlocks.CHORUS_CHEST, chestMaterial("chorus"));
        builder.put(ModBlocks.TRAPPED_CHORUS_CHEST, trappedMaterial("chorus"));
        builder.put(ModBlocks.CORRUPT_CHORUS_CHEST, chestMaterial("corrupt_chorus"));
        builder.put(ModBlocks.TRAPPED_CORRUPT_CHORUS_CHEST, trappedMaterial("corrupt_chorus"));
        builder.put(ModBlocks.RAIDING_CHEST, chestMaterial("raiding"));
        builder.put(ModBlocks.TRAPPED_RAIDING_CHEST, trappedMaterial("raiding"));
        MATERIALS = builder.build();
    }

    public ModChestRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Material getMaterial(T blockEntity, ChestType chestType) {
        EnumMap<ChestType, Material> b = MATERIALS.get(blockEntity.getBlockState().getBlock());

        var defaultMaterial = Sheets.chooseMaterial(blockEntity, chestType, ((ChestRendererAccessor) this).goety$xmasTextures());

        if (b == null) return defaultMaterial;

        Material material = b.get(chestType);

        return material != null ? material : defaultMaterial;
    }

    private static EnumMap<ChestType, Material> chestMaterial(String type) {
        EnumMap<ChestType, Material> map = new EnumMap<>(ChestType.class);

        map.put(ChestType.SINGLE, new Material(Sheets.CHEST_SHEET, Goety.location("entity/chest/" + type + "/" + "normal")));
        map.put(ChestType.LEFT, new Material(Sheets.CHEST_SHEET, Goety.location("entity/chest/" + type + "/normal_left")));
        map.put(ChestType.RIGHT, new Material(Sheets.CHEST_SHEET, Goety.location("entity/chest/" + type + "/normal_right")));

        return map;
    }

    private static EnumMap<ChestType, Material> trappedMaterial(String type) {
        EnumMap<ChestType, Material> map = new EnumMap<>(ChestType.class);

        map.put(ChestType.SINGLE, new Material(Sheets.CHEST_SHEET, Goety.location("entity/chest/" + type + "/" + "trapped")));
        map.put(ChestType.LEFT, new Material(Sheets.CHEST_SHEET, Goety.location("entity/chest/" + type + "/trapped_left")));
        map.put(ChestType.RIGHT, new Material(Sheets.CHEST_SHEET, Goety.location("entity/chest/" + type + "/trapped_right")));

        return map;
    }
}
