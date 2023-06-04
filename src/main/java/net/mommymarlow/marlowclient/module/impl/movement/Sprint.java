package net.mommymarlow.marlowclient.module.impl.movement;

import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.setting.ModeSetting;
import net.mommymarlow.marlowclient.utils.ColorUtils;
import net.mommymarlow.marlowclient.utils.PlayerUtils;

public class Sprint extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Omni", "Vanilla", "Vanilla2");

    public Sprint() {
        super("Sprint  ", "  sprint all the time", Category.MOVEMENT);
        addSettings(mode);
    }

    @Override
    public void onMotion() {
        this.setDisplayName("Sprint " + ColorUtils.gray + mode.getMode());
        if (mc.player != null && mc.player.input != null) {
            if (mode.isMode("Vanilla")) mc.options.sprintKey.setPressed(true);
            else if (mode.isMode("Omni") && PlayerUtils.isMoving()) mc.player.setSprinting(true);
            else if (mode.isMode("Vanilla2")) mc.player.setSprinting(true);
        }
        super.onMotion();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
