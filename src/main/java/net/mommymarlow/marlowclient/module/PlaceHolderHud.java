package net.mommymarlow.marlowclient.module;

import net.minecraft.client.util.math.MatrixStack;

public class PlaceHolderHud extends HudModule{
    public PlaceHolderHud(String name, Category category) {
        super(name, "Hud description", category, 0, 0, 10, 10);
    }

    @Override
    public void draw(MatrixStack matrices) {
        mc.textRenderer.draw(matrices, getDisplayName(), getX(), getY(), -1);
        setWidth(mc.textRenderer.getWidth(getDisplayName()));
        setHeight(mc.textRenderer.fontHeight);
    }
}
