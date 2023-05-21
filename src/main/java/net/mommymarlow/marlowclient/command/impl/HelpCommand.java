package net.mommymarlow.marlowclient.command.impl;

import net.minecraft.text.Text;
import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.command.Command;
import net.mommymarlow.marlowclient.command.CommandManager;

import java.util.ArrayList;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Lists all commands.", "help");
    }

    @Override
    public void onCmd(String message, String[] args) {
        ArrayList<Command> commands = new ArrayList<>(CommandManager.INSTANCE.getCmds());
        for (int i = 1; i< Marlow.commandCount; i++){
            mc.player.sendMessage(Text.literal(commands.get(i).getName()));
        }
    }
}
