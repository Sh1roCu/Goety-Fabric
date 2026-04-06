package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.function.Supplier;

public class ModPaintings {

    public static final PaintingVariant APOSTLE = register("apostle", () -> new PaintingVariant(16, 32));
    public static final PaintingVariant MOVIE = register("movie", () -> new PaintingVariant(16, 32));
    public static final PaintingVariant KNUCKLES = register("knuckles", () -> new PaintingVariant(16, 32));
    public static final PaintingVariant SATURN = register("saturn", () -> new PaintingVariant(16, 32));
    public static final PaintingVariant WICKER = register("wicker", () -> new PaintingVariant(16, 32));
    public static final PaintingVariant BEGGING = register("begging", () -> new PaintingVariant(16, 32));
    public static final PaintingVariant ELDRITCH = register("eldritch", () -> new PaintingVariant(16, 32));
    public static final PaintingVariant CRYPT = register("crypt", () -> new PaintingVariant(32, 16));
    public static final PaintingVariant STONEDRAKE = register("stonedrake", () -> new PaintingVariant(32, 16));
    public static final PaintingVariant LIZARDCALM = register("lizardcalm", () -> new PaintingVariant(32, 16));
    public static final PaintingVariant MINISTER = register("minister", () -> new PaintingVariant(48, 32));
    public static final PaintingVariant FENG = register("feng", () -> new PaintingVariant(48, 32));
    public static final PaintingVariant FALLEN_KINGDOM = register("fallen_kingdom", () -> new PaintingVariant(48, 32));
    public static final PaintingVariant LADIES_OF_THE_WOOD = register("ladies_of_the_wood", () -> new PaintingVariant(48, 32));
    public static final PaintingVariant CAPSTONE = register("capstone", () -> new PaintingVariant(48, 32));
    public static final PaintingVariant MRAEG_JOEY = register("mraeg_joey", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant MRAEG_WALLY = register("mraeg_wally", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant REVELATION = register("revelation", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant THEPANTS = register("thepants", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant MANSION = register("mansion", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant RUBY = register("ruby", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant HOUND = register("hound", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant KOGANUSAN = register("koganusan", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant GLACIAL_FLOWER = register("glacial_flower", () -> new PaintingVariant(32, 32));
    public static final PaintingVariant WRAITH = register("wraith", () -> new PaintingVariant(16, 16));
    public static final PaintingVariant BOBBY = register("bobby", () -> new PaintingVariant(16, 16));
    public static final PaintingVariant HEART = register("heart", () -> new PaintingVariant(16, 16));
    public static final PaintingVariant TORMENT = register("torment", () -> new PaintingVariant(16, 16));

    public static void init() {

    }

    private static PaintingVariant register(String name, Supplier<PaintingVariant> supplier) {
        return Registry.register(BuiltInRegistries.PAINTING_VARIANT, Goety.location(name), supplier.get());
    }
}
