package net.mommymarlow.marlowclient.command.impl;

import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.command.Command;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix", "changes the prefix of the commands from marlow client", "prefix");
    }

    @Override
    public void onCmd(String message, String[] args) {
        Marlow.setCommandPrefix(args[1]);
    }
}
