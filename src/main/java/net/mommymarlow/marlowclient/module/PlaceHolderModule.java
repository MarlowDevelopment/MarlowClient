package net.mommymarlow.marlowclient.module;

import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.setting.ModeSetting;
import net.mommymarlow.marlowclient.setting.NumberSetting;

public class PlaceHolderModule extends Module {

    BooleanSetting booleanSetting = new BooleanSetting("Boolean", false);
    NumberSetting numberSetting = new NumberSetting("Number", 1, 10, 1, 1);
    ModeSetting modeSetting = new ModeSetting("Mode", "Mode1", "Mode1", "Mode2", "Mode3");

    public PlaceHolderModule(String name, Category category) {
        super(name, "Description ", category);
        addSettings(booleanSetting, numberSetting, modeSetting);
    }
}
