package net.mommymarlow.marlowclient.module.impl.render;

import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;

public class NickPing extends Module {

    public NickPing() {
        super("NickPing  ","  shows others ping", Category.RENDER);
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

}