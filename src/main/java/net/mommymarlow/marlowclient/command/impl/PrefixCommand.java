package net.mommymarlow.marlowclient.command.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.client.MarlowClient;
import net.mommymarlow.marlowclient.command.Command;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix", "changes the prefix of the commands from marlow client", "prefix");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("new prefix", StringArgumentType.greedyString()).executes(context -> {
            String prefix = context.getArgument("new prefix", String.class);
            MarlowClient.setCommandPrefix(prefix);

            return SINGLE_SUCCESS;
        }));
    }

}
