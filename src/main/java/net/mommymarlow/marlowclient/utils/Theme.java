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

    public static int redEnable;
    public static int greenEnable;
    public static int blueEnable;

    public static int red1Enable;
    public static int green1Enable;
    public static int blue1Enable;

    public static int checkboxR;
    public static int checkboxG;
    public static int checkboxB;

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

        redEnable = 41;
        greenEnable =117;
        blueEnable = 221;
        red1Enable = 33;
        green1Enable =94;
        blue1Enable = 181;
        checkboxR = 33;
        checkboxG = 94;
        checkboxB = 181;
    }

    public static void lightTheme() {
        WINDOW_COLOR = new Color(218, 218, 218);
        ENABLED = new Color(162, 73, 73);

        UNFOCUSED_TEXT_COLOR = new Color(108, 108, 108);
        //DISABLED_TEXT_COLOR = ;

        SETTINGS_BG = new Color(218, 218, 218);
        SETTINGS_HEADER = new Color(122, 122, 122);

        MODULE_ENABLED_BG_HOVER = new Color(101, 101, 101);
        MODULE_DISABLED_BG_HOVER = new Color(101, 101, 101);

        MODULE_ENABLED_BG = new Color(112, 112, 112);
        MODULE_DISABLED_BG = new Color(168, 168, 168);
        MODULE_COLOR = new Color(134, 134, 134);
        MODULE_TEXT = new Color(26, 26, 26);

        TOGGLE_BUTTON_BG = new Color(114, 114, 114);
        TOGGLE_BUTTON_FILL = new Color(152, 152, 152);

        NORMAL_TEXT_COLOR = new Color(26, 26, 26);

        MODE_SETTING_BG = new Color(138, 138, 138);
        MODE_SETTING_FILL = new Color(138, 138, 138);

        SLIDER_SETTING_BG = new Color(138, 138, 138);

        CONFIG_EDIT_BG = new Color(218, 218, 218);


        redEnable = 162;
        blueEnable =73;
        greenEnable = 73;
        red1Enable = 162;
        blue1Enable =73;
        green1Enable = 73;
        checkboxR = 162;
        checkboxG = 73;
        checkboxB = 73;
    }
}
