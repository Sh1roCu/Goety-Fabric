package com.Polarice3.Goety.init;

import com.Polarice3.Goety.common.commands.GoetyCommand;
import com.Polarice3.Goety.common.commands.LichCommand;
import com.Polarice3.Goety.common.listeners.IllagerAssaultListener;
import com.Polarice3.Goety.common.listeners.SoulTakenListener;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.packs.PackType;

public class InitEvents {

    public static void init() {
        CommandRegistrationCallback.EVENT.register(InitEvents::onRegisterCommandEvent);
        registerListeners();
    }

    private static void onRegisterCommandEvent(CommandDispatcher<CommandSourceStack> commandDispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        LichCommand.register(commandDispatcher);
        GoetyCommand.register(commandDispatcher, registryAccess);
    }

    private static void registerListeners() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IllagerAssaultListener());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SoulTakenListener());
    }
}
