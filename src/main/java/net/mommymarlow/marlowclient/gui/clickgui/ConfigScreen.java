package net.mommymarlow.marlowclient.gui.clickgui;

import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.client.MarlowClient;
import net.mommymarlow.marlowclient.config.Config;
import net.mommymarlow.marlowclient.config.ConfigLoader;
import net.mommymarlow.marlowclient.gui.clickgui.components.Component;
import net.mommymarlow.marlowclient.utils.Animation;
import net.mommymarlow.marlowclient.utils.RenderUtils;
import net.mommymarlow.marlowclient.utils.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.awt.*;
import java.io.IOException;

public class ConfigScreen extends Component {

    // Access to orig menu
    public final MarlowClickGui parent;

    // Bro this animation
    private Animation createNewAnimX, createNewAnimY;

    // If config is added
    private boolean isConfigAdded = false;

    public EditConfigMenu editConfigMenu;

    public ConfigScreen(MarlowClickGui parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Get the Clickguis coordinates
        float pX = parent.windowX, pY = parent.windowY, pW = parent.width, pH = parent.height;

        // "Your profiles" text
        mc.textRenderer.draw(matrices, "Your Profiles", pX + 20, pY + 50, Theme.MODULE_TEXT.getRGB());
        // Blue underline
        RenderUtils.renderRoundedQuad(matrices, pX + 20, pY + 50 + mc.textRenderer.fontHeight, pX + 30,  pY + 50 + mc.textRenderer.fontHeight + 2, 1, 20, Theme.ENABLED);

        // Plus button
        RenderUtils.drawScaledTexturedRect(matrices, pX + pW - 48, pY + pH - 50, 0.07f, "textures/add_button.png");

        matrices.push();
        matrices.scale(0.05f, 0.05f, 0);
        RenderUtils.drawTexturedRectangle(matrices, (pX + pW - 35) * 20, (pY + 10) * 20, "textures/reload.png");
        matrices.pop();

        // Draw Configs
        int offsetX = 0;
        int offsetY = 0;
        for(Config config : ConfigLoader.getConfigs()) {

            // "Add a config" animation
            if(isConfigAdded && config == ConfigLoader.lastAddedConfig) {
                if(createNewAnimX != null && createNewAnimY != null) {
                    createNewAnimX.update(true, 3);
                    createNewAnimY.update(true, 3);

                    // Draw box
                    RenderUtils.renderRoundedQuad(matrices, createNewAnimX.getValue(), createNewAnimY.getValue(), createNewAnimX.getValue() + 80, createNewAnimY.getValue() + 80, 5, 20, Theme.SETTINGS_HEADER);
                    mc.textRenderer.draw(matrices, config.getName(), createNewAnimX.getValue() + 5, createNewAnimY.getValue() + 5, -1);

                    // Draw the description
                    drawStringWrapped(matrices, config.getDescription(), (int) createNewAnimX.getValue() + 5, (int) (createNewAnimY.getValue() + mc.textRenderer.fontHeight + 10), 70);

                    // Stop animation
                    if(createNewAnimX.hasEnded() && createNewAnimY.hasEnded()) {
                        createNewAnimX = null;
                        createNewAnimY = null;
                        isConfigAdded = false;
                    }
                }
            }
            else {
                // TODO: Maybe add a fading animation
                // If the Config is selected
                if (config == MarlowClient.selectedConfig) {
                    RenderUtils.renderRoundedQuad(matrices, pX + offsetX + 17, pY + 72 + offsetY, pX + 103 + offsetX, pY + 158 + offsetY, 5, 20, Color.WHITE);
                }

                RenderUtils.renderRoundedQuad(matrices, pX + offsetX + 20, pY + 75 + offsetY, pX + 100 + offsetX, pY + 155 + offsetY, 5, 20, Theme.SETTINGS_HEADER);
                mc.textRenderer.draw(matrices, config.getName(), pX + offsetX + 25, pY + 80 + offsetY, -1);

                // Edit Icon
                matrices.push();
                // With below 1 values its weird, you have to multiply by 1 / scale (1 / 0.025 = 40)
                matrices.scale(0.025f, 0.025f, 0);
                RenderUtils.drawTexturedRectangle(matrices, (pX + offsetX + 25) * 40, (pY + offsetY + 136) * 40, "textures/edit.png");
                matrices.pop();

                // Draw the description
                drawStringWrapped(matrices, config.getDescription(), (int) pX + offsetX + 25, (int) pY + 85 + mc.textRenderer.fontHeight + offsetY, 70);
            }

            offsetX += 90;

            // Max. 5 configs next to each other, so loop em down
            if (offsetX >= 450) {
                offsetX = 0;
                offsetY = 90;
            }
        }

        if(editConfigMenu != null) editConfigMenu.drawScreen(matrices, mouseX, mouseY, delta);
    }

    // ChatGPT method I had to alter a bit to draw a wrapped string
    private void drawStringWrapped(MatrixStack matrices, String text, int x, int y, int boxSize) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Text textObject = Text.of(text);
        OrderedText[] lines = mc.textRenderer.wrapLines(textObject, boxSize).toArray(new OrderedText[0]);

        int offset = 0;
        for(OrderedText orderedText : lines) {
            mc.textRenderer.draw(matrices, orderedText, x, y + offset, 0xFFFFFFFF);
            offset+= mc.textRenderer.fontHeight;
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {

        // Just some vars
        float pX = parent.windowX, pY = parent.windowY, pW = parent.width, pH = parent.height;

        if(editConfigMenu != null) {
            editConfigMenu.mouseClicked(mouseX, mouseY, button);
            return;
        }

        // Add a new config
        if(isHovered(pX + pW - 45, pY + pH - 45, pX + pW - 15, pY + pH - 15, mouseX, mouseY) && button == 0) {
            Config newConfig = new Config("Config Name", "Config Description");
            try {
                newConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            addConfig(newConfig);
        }

        // Reload
        if(isHovered(pX + pW - 40, pY + 10, pX + pW - 10, pY + 40, mouseX, mouseY) && button == 0) {
            try {
                ConfigLoader.loadConfigs();
                mc.player.sendMessage(Text.of("§aReloaded Configs!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int offsetX = 0;
        int offsetY = 0;
        for(Config config : ConfigLoader.getConfigs()) {

            // Edit the config
            if(isHovered(pX + offsetX + 25, pY + offsetY + 136, pX + offsetX + 40, pY + offsetY + 150, mouseX, mouseY) && button == 0) {
                editConfigMenu = new EditConfigMenu(this, config);
            }

            if(isHovered(pX + pW - 45, pY + pH - 45, pX + pW - 15, pY + pH - 15, mouseX, mouseY) && button == 0) {
                createNewAnimX = new Animation((int) (pX + pW - 35) , (int) pX + offsetX + 20);
                createNewAnimY = new Animation((int) (pY + pH - 37), (int) pY + 75 + offsetY);
            }

            if(isHovered(pX + offsetX + 17, pY + 72 + offsetY, pX + 103 + offsetX, pY + 158 + offsetY, mouseX, mouseY) && button == 0) {
                // Before changing we have to save the one before
                try {
                    MarlowClient.selectedConfig.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MarlowClient.selectedConfig = config;
                // *Tries* to load the config
                try {
                    config.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            offsetX += 90;

            // Max. 5 configs next to each other, so loop em down (you can have max 10 configs, sometime I'll make it more TODO: >10 configs
            if(offsetX >= 450) {
                offsetX = 0;
                offsetY = 90;
            }
        }
    }


    public void addConfig(Config config) {
        ConfigLoader.addConfig(config);
        isConfigAdded = true;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if(editConfigMenu != null) editConfigMenu.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void charTyped(char chr, int modifiers) {
        if(editConfigMenu != null) editConfigMenu.charTyped(chr, modifiers);
    }
}
