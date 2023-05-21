package net.mommymarlow.marlowclient.gui.clickgui;

import net.mommymarlow.marlowclient.config.Config;
import net.mommymarlow.marlowclient.config.ConfigLoader;
import net.mommymarlow.marlowclient.gui.clickgui.components.TextComponent;
import net.mommymarlow.marlowclient.utils.Animation;
import net.mommymarlow.marlowclient.utils.RenderUtils;
import net.mommymarlow.marlowclient.utils.Theme;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.io.IOException;

public class EditConfigMenu extends net.mommymarlow.marlowclient.gui.clickgui.components.Component {

    private final ConfigScreen parent;
    private Config editing;

    private final Animation animation;
    private final TextComponent nameText;
    private final TextComponent descriptionText;

    private boolean close;

    public EditConfigMenu(ConfigScreen parent, Config editing) {
        this.parent = parent;
        this.editing = editing;
        animation = new Animation(0, 50);
        nameText = new TextComponent(parent.parent, editing.getName());
        descriptionText = new TextComponent(parent.parent, editing.getDescription());
        close = false;
    }

    @Override
    public void drawScreen(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Get an outro value

        float pX = parent.parent.windowX, pY = parent.parent.windowY, pWidth = parent.parent.width, pHeight = parent.parent.height;

        animation.update(false, 3);

        // Outline
        RenderUtils.renderRoundedQuad(matrices,
                pX + 197 - animation.getValue(),
                pY + 147 - animation.getValue(),
                pX + 303 + animation.getValue(),
                pY + 153 + animation.getValue(), 10, 20, Color.white);

        RenderUtils.renderRoundedQuad(matrices,
                pX + 200 - animation.getValue(),
                pY + 150 - animation.getValue(),
                pX + 300 + animation.getValue(),
                pY + 150 + animation.getValue(), 10, 20, Theme.CONFIG_EDIT_BG);

        // Close the editing menu
        if(animation.hasEnded() && close) {
            parent.editConfigMenu = null;
            return;
        }

        if(animation.hasEnded() && !close) {
            mc.textRenderer.draw(matrices, "Name", pX + 158, pY + 110, Theme.MODULE_TEXT.getRGB());
            nameText.draw(matrices, pX + 155, pY + 120, 12);

            mc.textRenderer.draw(matrices, "Description", pX + 158, pY + 135, Theme.MODULE_TEXT.getRGB());
            descriptionText.draw(matrices, pX + 155, pY + 146, 12);

            matrices.push();
            matrices.scale(1.4f, 1.4f, 0);
            int hover = isHovered(pX + 337, pY + 103, pX + 346, pY + 112, mouseX, mouseY) ? -1 : Theme.MODULE_TEXT.getRGB();
            mc.textRenderer.draw(matrices, "x", (pX + 335) / 1.4f, (pY + 103) / 1.4f, hover);
            matrices.pop();

            // TODO: hover effect
            mc.textRenderer.draw(matrices, "Delete", pX + 315, pY + 185, -1);
        }

        if(!nameText.isFocused()) editing.setName(nameText.getText());
        if(!descriptionText.isFocused()) editing.setDescription(descriptionText.getText());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        float pX = parent.parent.windowX, pY = parent.parent.windowY, pWidth = parent.parent.width, pHeight = parent.parent.height;

        // Close
        if(isHovered(pX + 337, pY + 103, pX + 346, pY + 112, mouseX, mouseY) && button == 0) {
            close();
        }

        // Delete
        if(isHovered(pX + 310, pY + 185, pX + 345, pY + 195, mouseX, mouseY) && button == 0) {
            if(editing == ConfigLoader.getDefaultConfig()) {
                // TODO: cannot delete default config
                return;
            }
            editing.delete();
            close();
            try {
                ConfigLoader.loadConfigs();
                mc.player.sendMessage(Text.of("§aReloaded Configs!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        nameText.mouseClicked(mouseX, mouseY, button);
        descriptionText.mouseClicked(mouseX, mouseY, button);
    }

    private void close() {
        animation.setEnd(7);
        close = true;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if(editing != ConfigLoader.getDefaultConfig()) {
            nameText.keyPressed(keyCode, scanCode, modifiers);
        }

        descriptionText.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void charTyped(char chr, int modifiers) {
        nameText.charTyped(chr, modifiers);
        descriptionText.charTyped(chr, modifiers);
    }
}
