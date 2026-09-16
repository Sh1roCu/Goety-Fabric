package com.Polarice3.Goety.common.world.structures;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.Locale;
import java.util.function.Supplier;

public class ModStructurePieces {

    public static StructurePieceType WRECKED_MANSION_PIECE = setTemplatePieceId(WreckedMansionPieces.MansionPiece::new, "wrecked_mansion_piece");

    private static StructurePieceType setFullContextPieceId(StructurePieceType type, String name) {
        return register(name.toLowerCase(Locale.ROOT), () -> type);
    }

    private static StructurePieceType setPieceId(StructurePieceType.ContextlessType piece, String name) {
        return setFullContextPieceId(piece, name);
    }

    private static StructurePieceType setTemplatePieceId(StructurePieceType.StructureTemplateType piece, String name) {
        return setFullContextPieceId(piece, name);
    }

    public static void init() {

    }

    private static StructurePieceType register(String name, Supplier<StructurePieceType> supplier) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, Goety.location(name), supplier.get());
    }

}
