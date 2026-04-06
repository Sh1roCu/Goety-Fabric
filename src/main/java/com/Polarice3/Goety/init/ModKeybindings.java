package com.Polarice3.Goety.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ModKeybindings {
    public static KeyMapping[] keyBindings = new KeyMapping[16];

    public static void init() {
        keyBindings[0] = register(new KeyMapping("key.goety.wand", GLFW.GLFW_KEY_Z, "key.goety.category"));
        keyBindings[1] = register(new KeyMapping("key.goety.focusCircle", GLFW.GLFW_KEY_X, "key.goety.category"));
        keyBindings[2] = register(new KeyMapping("key.goety.bag", GLFW.GLFW_KEY_C, "key.goety.category"));
        keyBindings[3] = register(new KeyMapping("key.goety.witch.robe", GLFW.GLFW_KEY_V, "key.goety.witch.category"));
        keyBindings[4] = register(new KeyMapping("key.goety.ceaseFire", GLFW.GLFW_KEY_B, "key.goety.category"));
        keyBindings[5] = register(new KeyMapping("key.goety.lich.magnet", GLFW.GLFW_KEY_R, "key.goety.lich.category"));
        keyBindings[6] = register(new KeyMapping("key.goety.lich.nightVision", GLFW.GLFW_KEY_M, "key.goety.lich.category"));
        keyBindings[7] = register(new KeyMapping("key.goety.witch.extractPotions", GLFW.GLFW_KEY_G, "key.goety.witch.category"));
        keyBindings[8] = register(new KeyMapping("key.goety.witch.brewBag", GLFW.GLFW_KEY_H, "key.goety.witch.category"));
        keyBindings[9] = register(new KeyMapping("key.goety.witch.brewCircle", GLFW.GLFW_KEY_J, "key.goety.witch.category"));
        keyBindings[10] = register(new KeyMapping("key.goety.mount.roar", GLFW.GLFW_KEY_R, "key.goety.mount.category"));
        keyBindings[11] = register(new KeyMapping("key.goety.mount.freeRoam", GLFW.GLFW_KEY_H, "key.goety.mount.category"));
        keyBindings[12] = register(new KeyMapping("key.goety.lich.lichForm", GLFW.GLFW_KEY_N, "key.goety.lich.category"));
        keyBindings[13] = register(new KeyMapping("key.goety.lich.laugh", GLFW.GLFW_KEY_K, "key.goety.lich.category"));
        keyBindings[14] = register(new KeyMapping("key.goety.activate_curio", GLFW.GLFW_KEY_G, "key.goety.category"));
        keyBindings[15] = register(new KeyMapping("key.goety.dismiss", GLFW.GLFW_KEY_KP_DECIMAL, "key.goety.category"));
    }

    private static KeyMapping register(KeyMapping keyMapping) {
        return KeyBindingHelper.registerKeyBinding(keyMapping);
    }

    public static KeyMapping wandSlot() {
        if (keyBindings[0] != null) {
            return keyBindings[0];
        } else {
            return null;
        }
    }

    public static KeyMapping wandCircle() {
        if (keyBindings[1] != null) {
            return keyBindings[1];
        } else {
            return null;
        }
    }

    public static KeyMapping brewCircle() {
        if (keyBindings[9] != null) {
            return keyBindings[9];
        } else {
            return null;
        }
    }

}
