package net.mommymarlow.marlowclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.mommymarlow.marlowclient.Marlow;
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
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class MarlowClient implements ClientModInitializer {


    public static MarlowClient INSTANCE = new MarlowClient();


    // Minecraft Client
    public static MinecraftClient mc = MinecraftClient.getInstance();

    // Event system
    public EventManager eventManager;

    // Version
    public static final String VERSION = "0.3";

    public static final MarlowSystem system = MarlowSystem.INSTANCE;

    // The Client name
    private static final String name = "MarlowClient";
    // Chat Prefix
    private static final String prefix = "\2476\247l[\247l\247f\247lM\2479\247la\247a\247lr\2472\247ll\247b\247lo\2473\247lw \247c\247lC\2474\247ll\247d\247li\2475\247le\247f\247ln\2471\247lt\2476\247l] \247a";
    // Command prefix
    private static  String commandPrefix = "#";
    // Commands Count
    public static int commandCount = 4;

    // The Config that is currently used
    public static Config selectedConfig;
    public AutoUpdate autoUpdate;


    public static final String KEY_CATEGORY = "Marlow Client";
    public static final KeyBinding KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Open VapeGUI Keybind",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            KEY_CATEGORY
    ));
    public static final KeyBinding HUDKEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Hud Editor Keybind",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    ));


    // This method gets called when the mod is loaded
    @Override
    public void onInitializeClient() {

        autoUpdate = AutoUpdate.INSTANCE;
        autoUpdate.doUpdate();


        //system.addListener(new ChatListenerEvent());
        system.addListener(new NetworkEventListener());


        // Loads configs if there are any

        try {
            ConfigLoader.loadConfigs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Theme.darkTheme();

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
    public static MarlowClient getINSTANCE() {
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
