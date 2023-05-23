package net.mommymarlow.marlowclient;


import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.mommymarlow.marlowclient.client.MarlowSystem;
import net.mommymarlow.marlowclient.config.Config;
import net.mommymarlow.marlowclient.config.ConfigLoader;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.NetworkEventListener;
import net.mommymarlow.marlowclient.gui.HudConfigScreen;
import net.mommymarlow.marlowclient.gui.clickgui.MarlowClickGui;
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


    // Minecraft Client
    public static MinecraftClient mc = MinecraftClient.getInstance();

    // Event system
    public EventManager eventManager;

    // Version
    public static final String VERSION = "0.1";

    public static final MarlowSystem system = MarlowSystem.INSTANCE;


    // Instance of this class
    private static final Marlow INSTANCE = new Marlow();

    // The Client name
    private static final String name = "MarlowClient";
    // Chat Prefix
    private static final String prefix = "&6&l[&l&f&lM&9&la&a&lr&2&ll&b&lo&3&lw &c&lC&4&ll&d&li&5&le&f&ln&1&lt&6&l] &a";
    // Command prefix
    private static  String commandPrefix = "#";
    // Commands Count
    public static int commandCount = 4;

    // The Config that is currently used
    public static Config selectedConfig;


    public static final String KEY_CATEGORY = "marlowclient.category.main";
    public static final String KEY_TRANSLATION = "marlowclient.menu.open_key";
    public static final KeyBinding KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_TRANSLATION,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            KEY_CATEGORY
    ));
    public static final KeyBinding HUDKEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "marlowclient.menu.hud_key",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    ));


    // This method gets called when the mod is loaded
    @Override
    public void onInitialize() {
        eventManager = EventManager.INSTANCE;

        AutoUpdate.doUpdate();

        //system.addListener(new ChatListenerEvent());
        system.addListener(new NetworkEventListener());


        // Loads configs if there are any
        try {
            ConfigLoader.loadConfigs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set Theme to dark (no others so far)
        Theme.darkTheme();

        System.out.println("Loaded " + name + "!");
    }

    // Open menus
    public void onKeyPress(int key, int action) {
        MinecraftClient mc = MinecraftClient.getInstance();
        // Check if the player is ingame
        if(mc.currentScreen == null) {
            if (KEY.wasPressed()){
                mc.setScreen(MarlowClickGui.getINSTANCE());
            }



            if (HUDKEY.wasPressed()) {
                // Open the Hud Config Menu
                mc.setScreen(new HudConfigScreen());

                // For Module toggling
                for(Module module : ModuleManager.INSTANCE.getModules()) {
                    if(module.getKey() == key) {
                        module.toggle();
                    }
                }
            }
        }
    }

    // Get the Instance of this class
    public static Marlow getINSTANCE() {
        return INSTANCE;
    }

    // Getters
    public static String getName() {
        return name;
    }

    public static String getCommandPrefix() {
        return commandPrefix;
    }

    public static void setCommandPrefix(String newPrefix){
        commandPrefix = newPrefix;
    }

    public static String getPrefix() {
        return prefix;
    }



}
