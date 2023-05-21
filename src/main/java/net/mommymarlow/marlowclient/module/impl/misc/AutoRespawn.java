package net.mommymarlow.marlowclient.module.impl.misc;

import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.SetScreenEvent;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;

public class AutoRespawn extends Module   {

    public AutoRespawn() {
        super("AutoRespawn  ", "  Clicks the respawn button for you", Category.MISC);
    }


    @Override
    public void onEnable() {
        system.addListener(this);
        super.onEnable();
    }


    @Override
    public void onDisable() {
        system.removeListener(this);
        super.onDisable();
    }

    @EventTarget
    public void onDeathScreen(SetScreenEvent e) {
        if (mc.player == null) return;

        if (e.getScreen() instanceof DeathScreen) {
            mc.player.requestRespawn();
            mc.setScreen((Screen)null);
        }
    }
}
