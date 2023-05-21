package net.mommymarlow.marlowclient.utils;

import java.awt.*;

public class Theme {

    public static Color WINDOW_COLOR;
    public static Color ENABLED;

    public static Color UNFOCUSED_TEXT_COLOR;
    public static Color DISABLED_TEXT_COLOR;

    public static Color SETTINGS_BG;
    public static Color SETTINGS_HEADER;

    public static Color MODULE_ENABLED_BG_HOVER;
    public static Color MODULE_DISABLED_BG_HOVER;

    public static Color MODULE_ENABLED_BG;
    public static Color MODULE_DISABLED_BG;
    public static Color MODULE_COLOR;
    public static Color MODULE_TEXT;

    public static Color TOGGLE_BUTTON_BG;
    public static Color TOGGLE_BUTTON_FILL;

    public static Color NORMAL_TEXT_COLOR;

    public static Color MODE_SETTING_BG;
    public static Color MODE_SETTING_FILL;

    public static Color SLIDER_SETTING_BG;

    public static Color CONFIG_EDIT_BG;

    // This is to change the Click gui theme
    public static void darkTheme() {
        WINDOW_COLOR = new Color(21, 22, 25);
        ENABLED = new Color(51, 112, 203);

        UNFOCUSED_TEXT_COLOR = new Color(108, 109, 113);
        //DISABLED_TEXT_COLOR = ;

        SETTINGS_BG = new Color(32, 31, 35);
        SETTINGS_HEADER = new Color(39, 38, 42);

        MODULE_ENABLED_BG_HOVER = new Color(43, 41, 45);
        MODULE_DISABLED_BG_HOVER = new Color(35, 35, 35);

        MODULE_ENABLED_BG = new Color(36, 34, 38);
        MODULE_DISABLED_BG = new Color(32, 31, 33);
        MODULE_COLOR = new Color(37, 35, 39);
        MODULE_TEXT = new Color(94, 95, 98);

        TOGGLE_BUTTON_BG = new Color(59, 60, 65);
        TOGGLE_BUTTON_FILL = new Color(29, 27, 31);

        NORMAL_TEXT_COLOR = Color.white;

        MODE_SETTING_BG = new Color(46, 45, 48);
        MODE_SETTING_FILL = new Color(32, 31, 35);

        SLIDER_SETTING_BG = new Color(73, 72, 76);

        CONFIG_EDIT_BG = new Color(20, 20, 25);
    }

    public static void lightTheme() {
        WINDOW_COLOR = new Color(221, 222, 225);
        ENABLED = new Color(203, 51, 51);
        UNFOCUSED_TEXT_COLOR = new Color(118, 118, 118);
        //DISABLED_TEXT_COLOR = ;

        SETTINGS_BG = new Color(158, 158, 158);
        SETTINGS_HEADER = new Color(167, 167, 167);

        MODULE_ENABLED_BG_HOVER = new Color(164, 164, 164);
        MODULE_DISABLED_BG_HOVER = new Color(222, 222, 222);

        MODULE_ENABLED_BG = new Color(149, 149, 149);
        MODULE_DISABLED_BG = new Color(211, 211, 211);
        MODULE_COLOR = new Color(215, 215, 215);
        MODULE_TEXT = new Color(155, 155, 155);

        TOGGLE_BUTTON_BG = new Color(127, 127, 127);
        TOGGLE_BUTTON_FILL = new Color(195, 195, 195);

        NORMAL_TEXT_COLOR = Color.darkGray;

        MODE_SETTING_BG = new Color(132, 132, 132);
        MODE_SETTING_FILL = new Color(174, 174, 174);

        SLIDER_SETTING_BG = new Color(121, 121, 121);

        CONFIG_EDIT_BG = new Color(183, 183, 183);


    }
}
