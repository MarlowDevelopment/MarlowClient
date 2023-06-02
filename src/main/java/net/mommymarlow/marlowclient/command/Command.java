package net.mommymarlow.marlowclient.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;
import net.mommymarlow.marlowclient.utils.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Uuids;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Command {

    private String name, description;
    private List<String> aliases;
    public ChatUtils chat = new ChatUtils();
    public MinecraftClient mc = MinecraftClient.getInstance();
    public int SINGLE_SUCCESS = com.mojang.brigadier.Command.SINGLE_SUCCESS;


    public Command(String name, String description, String...aliases) {
        this.name = name;
        this.description = description;
        this.aliases = Arrays.asList(aliases);
        mc = MinecraftClient.getInstance();
    }


    protected static <T> RequiredArgumentBuilder<CommandSource, T> argument(final String name, final ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    protected static LiteralArgumentBuilder<CommandSource> literal(final String name) {
        return LiteralArgumentBuilder.literal(name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public boolean isCreative() {
        return mc.player.getAbilities().creativeMode;
    }

    public void creativeError() {
        chat.sendMsg("&cYou need to be in creative mode!");
    }


    public void genError(Exception e) {
        chat.sendMsg("&cSomething went wrong");
        chat.sendMsg(e.getMessage());
    }


    public double[] getCoords(String x, String y, String z) {

        double xCoord;
        double yCoord;
        double zCoord;

        if(x.equalsIgnoreCase("~")) xCoord = mc.player.getX();
        else xCoord = Double.parseDouble(x);
        if(y.equalsIgnoreCase("~")) yCoord = mc.player.getY();
        else yCoord = Double.parseDouble(y);
        if(z.equalsIgnoreCase("~")) zCoord = mc.player.getZ();
        else zCoord = Double.parseDouble(z);

        return new double[]{xCoord, yCoord, zCoord};
    }

    public double getX(String txt) {
        if(txt.equalsIgnoreCase("~")) return mc.player.getX();
        else return Double.parseDouble(txt);
    }

    public double getY(String txt) {
        if(txt.equalsIgnoreCase("~")) return mc.player.getY();
        else return Double.parseDouble(txt);
    }

    public double getZ(String txt) {
        if(txt.equalsIgnoreCase("~")) return mc.player.getZ();
        else return Double.parseDouble(txt);
    }

    public int[] convertUUID(UUID uuid) {
        return Uuids.toIntArray(uuid);
    }

    public int[] convertUUID(String txt) {
        UUID uuid1 = UUID.fromString(txt);
        return Uuids.toIntArray(uuid1);
    }

    public String coordsToText(double x, double y, double z) {
        return "Pos:[" + x + "," + y + "," + z + "]";
    }

    public String uuidToText(UUID id) {
        int[] uuid = convertUUID(id);

        int one = uuid[0];
        int two = uuid[1];
        int three = uuid[2];
        int four = uuid[3];

        return "[I;" + one + "," + two + "," + three + "," + four + "]";
    }

    public final void registerTo(CommandDispatcher<CommandSource> dispatcher) {
        register(dispatcher, name);
        for (String alias : aliases) register(dispatcher, alias);
    }

    public void register(CommandDispatcher<CommandSource> dispatcher, String name) {
        LiteralArgumentBuilder<CommandSource> builder = LiteralArgumentBuilder.literal(name);
        build(builder);
        dispatcher.register(builder);
    }

    public abstract void build(LiteralArgumentBuilder<CommandSource> builder);
}
