package net.mommymarlow.marlowclient.module.impl.render;

import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;

public class NickHider extends Module {

    private static String replacementUsername = "Marlowww";
    private static boolean isEnabledStatic = false;


    public NickHider() {
        super("NickHider  ", "  Hides your name in chat.",  Category.RENDER);
    }



    @Override
    public void onEnable() {
        system.addListener(this);
        isEnabledStatic = true;
        super.onEnable();
    }


    @Override
    public void onDisable() {
        system.removeListener(this);
        isEnabledStatic = false;
        super.onDisable();
    }

    public static int setReplacementUsername(String newReplacementUsername) {
        replacementUsername = newReplacementUsername;
        return 0;
    }

    public static String replaceName(String string) {
        if (string != null && isEnabledStatic) {
            return string.replace(mc.getSession().getUsername(), replacementUsername);
        }
        return string;
    }
}
