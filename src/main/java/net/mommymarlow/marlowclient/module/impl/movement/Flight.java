package net.mommymarlow.marlowclient.module.impl.movement;

import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;

public class Flight extends Module {
    public Flight(String name, String description, Category category) {
        super(name, description, category);
    }

    @Override
    public void onEnable() {
        system.addListener(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.player.getAbilities().flying = true;
        system.removeListener(this);
        super.onDisable();
    }

    @EventTarget
    private void onUpdate(EventUpdate e){
        mc.player.getAbilities().flying = true;
    }
}
