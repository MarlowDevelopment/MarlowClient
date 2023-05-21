package net.mommymarlow.marlowclient.command.impl;

import net.minecraft.text.Text;
import net.mommymarlow.marlowclient.command.Command;
import net.mommymarlow.marlowclient.utils.Theme;

public class ThemeCommand extends Command {

    public ThemeCommand() {
        super("theme", "Changes the theme", "theme");
    }

    @Override
    public void onCmd(String message, String[] args) {
       String themeName = args[1];

        if (themeName.isBlank())
        {
            Text.literal("§cIncorrect usage / no theme found : #theme light/dark");
        }else
        {
            if (themeName.equalsIgnoreCase("dark")) {
                Theme.darkTheme();
            } else if (themeName.equalsIgnoreCase("light")) {
                Theme.lightTheme();
            }else
            {
                mc.player.sendMessage(Text.literal("§cIncorrect usage / no theme found : #theme light/dark"));
            }
        }
    }
}
