package net.mommymarlow.marlowclient.module.impl.render;

import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.setting.NumberSetting;

public class FullBright extends Module {

    public FullBright()
    {
        super("FullBright  ", "  override gamma value", Category.RENDER);
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
