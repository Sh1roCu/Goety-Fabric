package com.Polarice3.Goety.init;

import com.Polarice3.Goety.client.render.ModPlayerRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ClientRendererInit {
    private ModPlayerRenderer renderer;

    private static final ClientRendererInit INSTANCE = new ClientRendererInit();

    public static ClientRendererInit getInstance() {
        return INSTANCE;
    }

    @UnknownNullability
    public ModPlayerRenderer getModPlayerRenderer() {
        return this.renderer;
    }

    public void onAddLayers(Map<EntityType<?>, EntityRenderer<?>> renderers, Map<String, EntityRenderer<? extends Player>> skinMap, EntityRendererProvider.Context context, EntityModelSet entityModelSet) {
        renderer = new ModPlayerRenderer(context);
    }
}
