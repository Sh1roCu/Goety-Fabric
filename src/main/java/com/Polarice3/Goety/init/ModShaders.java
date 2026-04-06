package com.Polarice3.Goety.init;

import com.Polarice3.Goety.Goety;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.io.UncheckedIOException;

@Environment(EnvType.CLIENT)
public final class ModShaders {

    private static ShaderInstance holeShader;

    public static void registerShaders(CoreShaderRegistrationCallback.RegistrationContext ctx) {
        try {
            ctx.register(new ResourceLocation(Goety.MOD_ID, "hole"), DefaultVertexFormat.POSITION, shader -> holeShader = shader);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public static ShaderInstance getHoleShader() {
        return holeShader;
    }

}
