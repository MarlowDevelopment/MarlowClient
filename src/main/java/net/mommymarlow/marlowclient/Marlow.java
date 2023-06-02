package net.mommymarlow.marlowclient;


import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.mommymarlow.marlowclient.client.MarlowClient;
import net.mommymarlow.marlowclient.client.MarlowSystem;
import net.mommymarlow.marlowclient.config.Config;
import net.mommymarlow.marlowclient.config.ConfigLoader;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.NetworkEventListener;
import net.mommymarlow.marlowclient.gui.HudConfigScreen;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.utils.AutoUpdate;
import net.mommymarlow.marlowclient.utils.Theme;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

// This is the main Plugin class
public class Marlow implements ModInitializer {


    @Override
    public void onInitialize() {
        try {
            System.out.println("Loading Marlow Client");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Marlow Client Loaded");
    }



}
