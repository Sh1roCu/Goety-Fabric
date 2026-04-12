package cn.sh1rocu.goety.util.forge;

import cn.sh1rocu.goety.api.event.CustomizeGuiOverlayEvent;
import cn.sh1rocu.goety.api.event.MovementInputUpdateEvent;
import cn.sh1rocu.goety.api.event.ViewportEvent;
import cn.sh1rocu.goety.api.extension.client.ICustomGenericArmorModel;
import cn.sh1rocu.goety.api.extension.client.ICustomArmorRenderer;
import cn.sh1rocu.goety.util.client.MinecraftUtil;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ClientHooks {

    public static CustomizeGuiOverlayEvent.BossEventProgress onCustomizeBossEventProgress(GuiGraphics guiGraphics, Window window, LerpingBossEvent bossInfo, int x, int y, int increment) {
        CustomizeGuiOverlayEvent.BossEventProgress evt = new CustomizeGuiOverlayEvent.BossEventProgress(window, guiGraphics,
                MinecraftUtil.getPartialTick(), bossInfo, x, y, increment);
        CustomizeGuiOverlayEvent.BOSS_EVENT_PROGRESS.invoker().post(evt);
        return evt;
    }

    public static void onFogRender(FogRenderer.FogMode mode, FogType type, Camera camera, float partialTick, float renderDistance, float nearDistance, float farDistance, FogShape shape) {
        FluidState state = camera.getEntity().level().getFluidState(camera.getBlockPosition());
//        if (camera.getPosition().y < (double) ((float) camera.getBlockPosition().getY() + state.getHeight(camera.getEntity().level(), camera.getBlockPosition())))
//            IClientFluidTypeExtensions.of(state).modifyFogRender(camera, mode, renderDistance, partialTick, nearDistance, farDistance, shape);

        ViewportEvent.RenderFog event = new ViewportEvent.RenderFog(mode, type, camera, partialTick, nearDistance, farDistance, shape);
        ViewportEvent.RENDER_FOG.invoker().post(event);
        if (event.isCanceled()) {
            RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
            RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
            RenderSystem.setShaderFogShape(event.getFogShape());
        }
    }

    public static void onMovementInputUpdate(Player player, Input movementInput) {
        MovementInputUpdateEvent.EVENT.invoker().post(new MovementInputUpdateEvent(player, movementInput));
    }

    public static Model getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> _default) {
        return itemStack.getItem() instanceof ICustomGenericArmorModel custom
                ? custom.getGenericArmorModel(entityLiving, itemStack, slot, _default)
                : getGenericArmorModel(entityLiving, itemStack, slot, _default);
    }

    public static String getArmorTexture(Entity entity, ItemStack armor, String _default, EquipmentSlot slot, String type) {
        String result = armor.getItem() instanceof ICustomArmorRenderer custom ? custom.getArmorTexture(armor, entity, slot, type) : _default;
        return result != null ? result : _default;
    }

    @NotNull
    private static Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        HumanoidModel<?> replacement = itemStack.getItem() instanceof ICustomArmorRenderer custom
                ? custom.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original)
                : getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
        if (replacement != original) {
            copyModelProperties(original, replacement);
            return replacement;
        }
        return original;
    }

    @NotNull
    private static HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        return original;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LivingEntity> void copyModelProperties(HumanoidModel<T> original, HumanoidModel<?> replacement) {
        original.copyPropertiesTo((HumanoidModel<T>) replacement);
        replacement.head.visible = original.head.visible;
        replacement.hat.visible = original.hat.visible;
        replacement.body.visible = original.body.visible;
        replacement.rightArm.visible = original.rightArm.visible;
        replacement.leftArm.visible = original.leftArm.visible;
        replacement.rightLeg.visible = original.rightLeg.visible;
        replacement.leftLeg.visible = original.leftLeg.visible;
    }
}
