package net.mommymarlow.marlowclient.command.impl;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.mommymarlow.marlowclient.command.Command;
import net.mommymarlow.marlowclient.utils.ChatUtils;
import net.mommymarlow.marlowclient.utils.Theme;

public class ThemeCommand extends Command {

    public ThemeCommand() {
        super("theme", "Changes the theme", "theme");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("theme name", StringArgumentType.greedyString()).executes(context -> {

            String themeName = context.getArgument("theme name", String.class);
            if (themeName.equalsIgnoreCase("light")){
                Theme.lightTheme();
                ChatUtils.sendSuccess("Theme set to light");
            } else if (themeName.equalsIgnoreCase("dark")) {
                Theme.darkTheme();
                ChatUtils.sendSuccess("Theme set to dark");
            }else {
                ChatUtils.sendErrorCommand("#theme <dark/light>");
            }


            return SINGLE_SUCCESS;
        }));
    }
}
