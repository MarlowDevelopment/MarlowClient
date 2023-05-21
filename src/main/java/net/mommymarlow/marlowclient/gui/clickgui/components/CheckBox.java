package net.mommymarlow.marlowclient.gui.clickgui.components;

import net.mommymarlow.marlowclient.gui.clickgui.MarlowClickGui;
import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.utils.Animation;
import net.mommymarlow.marlowclient.utils.RenderUtils;
import net.mommymarlow.marlowclient.utils.Theme;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class CheckBox extends Component {

    private final BooleanSetting setting;
    private final MarlowClickGui parent;
    private float x, y;

    private final Animation animation;

    public CheckBox(BooleanSetting setting, MarlowClickGui parent, float x, float y) {
        this.setting = setting;
        this.parent = parent;
        this.x = x;
        this.y = y;
        animation = new Animation(0, 100);
    }

    @Override
    public void drawScreen(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if(setting.isEnabled()) {
            mc.textRenderer.draw(matrices, setting.getName(), x + parent.windowX + 445 + parent.settingsFieldX, y + 4, -1);
            animation.setEnd(100);
            Color color = new Color(33, 94, 181, (int) (animation.getValue() / 100 * 255));
            RenderUtils.renderRoundedQuad(matrices, x + parent.windowX + parent.width - 30, y + 2, x + parent.windowX + parent.width - 10, y + 12, 4, 20, color);
            // Circle
            RenderUtils.drawCircle(matrices, x + parent.windowX + parent. width - 25 + 10 * (animation.getValue() / 100f), y + 7, 3.5, 10, -1);
        }
        else {
            mc.textRenderer.draw(matrices, setting.getName(), x + parent.windowX + 445 + parent.settingsFieldX, y + 4, Theme.SLIDER_SETTING_BG.getRGB());
            animation.setEnd(0);

            RenderUtils.renderRoundedQuad(matrices,x + parent.windowX + parent.width - 30, y + 2, x + parent.windowX + parent.width - 10, y + 12, 4, 20, Theme.TOGGLE_BUTTON_BG);
            RenderUtils.renderRoundedQuad(matrices, x + parent.windowX + parent.width - 29, y + 3, x + parent.windowX + parent.width - 11, y + 11, 3, 20, Theme.MODE_SETTING_FILL);
            // Cirlce
            RenderUtils.drawCircle(matrices, x + parent.windowX + parent.width - 25 + 10 * (animation.getValue() / 100f), y + 7, 3.5, 10, Theme.TOGGLE_BUTTON_BG.getRGB());
        }

        animation.update();
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if(isHovered(x + parent.windowX + parent.width - 30, y + 2, x + parent.windowX + parent.width - 10, y + 12, mouseX, mouseY)) {
            setting.toggle();
        }
    }

    public void setY(float y) {
        this.y = y;
    }
}
