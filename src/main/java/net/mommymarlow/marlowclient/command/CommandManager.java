package net.mommymarlow.marlowclient.command;

import net.mommymarlow.marlowclient.command.impl.HelpCommand;
import net.mommymarlow.marlowclient.command.impl.PrefixCommand;
import net.mommymarlow.marlowclient.command.impl.ThemeCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    public static CommandManager INSTANCE = new CommandManager();
    private List<Command> cmds = new ArrayList<>();

    public CommandManager() {
        init();
    }

    private void init() {
        add(new ThemeCommand());
        add(new PrefixCommand());
        add(new HelpCommand());
    }

    public void add(Command command) {
        if(!cmds.contains(command)) {
            cmds.add(command);
        }
    }

    public void remove(Command command){
        cmds.remove(command);
    }

    public List<Command> getCmds() {
        return cmds;
    }

}
