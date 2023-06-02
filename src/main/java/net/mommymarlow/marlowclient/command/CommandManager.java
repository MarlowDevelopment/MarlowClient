package net.mommymarlow.marlowclient.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import net.mommymarlow.marlowclient.command.impl.PrefixCommand;
import net.mommymarlow.marlowclient.command.impl.ThemeCommand;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.mommymarlow.marlowclient.client.MarlowClient.mc;

public class CommandManager {

    public static CommandManager INSTANCE = new CommandManager();
    private final CommandDispatcher<CommandSource> DISPATCHER = new CommandDispatcher<>();
    private final CommandSource COMMAND_SOURCE = new ClientCommandSource(mc.getNetworkHandler(), mc);
    private final List<Command> commands = new ArrayList<>();
    private final Map<Class<? extends Command>, Command> commandInstances = new HashMap<>();

    private List<Command> cmds = new ArrayList<>();

    public CommandManager() {
        init();
    }

    private void init() {
        add(new ThemeCommand());
        add(new PrefixCommand());
    }

    public void add(Command command) {
        commands.removeIf(command1 -> command1.getName().equals(command.getName()));
        commandInstances.values().removeIf(command1 -> command1.getName().equals(command.getName()));

        command.registerTo(DISPATCHER);
        commands.add(command);
        commandInstances.put(command.getClass(), command);
    }

    public void remove(Command command){
        cmds.remove(command);
    }

    public void onCmd(String message) throws CommandSyntaxException {
        onCmd(message, new ClientCommandSource(mc.getNetworkHandler(), mc));
    }

    public void onCmd(String message, CommandSource source) throws CommandSyntaxException {
        ParseResults<CommandSource> results = DISPATCHER.parse(message, source);
        DISPATCHER.execute(results);
    }
    public List<Command> getCmds() {
        return cmds;
    }

    public CommandDispatcher<CommandSource> getDispatcher() {
        return DISPATCHER;
    }

    public CommandSource getCommandSource() {
        return COMMAND_SOURCE;
    }
}
