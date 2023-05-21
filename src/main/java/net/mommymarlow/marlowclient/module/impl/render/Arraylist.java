package net.mommymarlow.marlowclient.module.impl.render;

import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.HudModule;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;
import java.util.List;

public class Arraylist extends HudModule {

    public Arraylist() {
        super("Arraylist  ", "  Shows a lsit of enabled Modules", Category.RENDER, 0, 0, 14, 14);
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

    @Override
    public void draw(MatrixStack matrices) {
        List<Module> enabledModules = ModuleManager.INSTANCE.getEnabledModules();
        // Sort
        enabledModules.sort((m1, m2) -> mc.textRenderer.getWidth(m2.getDisplayName()) - mc.textRenderer.getWidth(m1.getDisplayName()));

        boolean leftHalf = getX() < (mc.getWindow().getScaledWidth() / 2);
        boolean rightHalf = getX() > (mc.getWindow().getScaledWidth() / 2);

        setWidth(mc.textRenderer.getWidth(enabledModules.get(0).getDisplayName()));

        if((getX() + getWidth()) > mc.getWindow().getScaledWidth()) {
            setX(mc.getWindow().getScaledWidth() - (getWidth()));
        }

        int offset = 0;
        for(Module module : enabledModules) {
            if(leftHalf) {
                mc.textRenderer.draw(matrices, module.getDisplayName(), getX(), getY() + offset, -1);
            }
            else if(rightHalf) {
                mc.textRenderer.draw(matrices, module.getDisplayName(), getX() + getWidth() - mc.textRenderer.getWidth(module.getDisplayName()), getY() + offset, -1);
            }
            offset += mc.textRenderer.fontHeight + 2;
        }

        setHeight(offset);
    }
}
