package net.mommymarlow.marlowclient.utils;

import net.mommymarlow.marlowclient.Marlow;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.mommymarlow.marlowclient.client.MarlowClient;

import static net.mommymarlow.marlowclient.client.MarlowClient.mc;

public class ChatUtils {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    // Unicode character for §
    private final String paragraph = "\u00A7";

    public void sendMsg(String text) {
        if(mc.player != null) mc.player.sendMessage(Text.of(translate(text)));
    }

    public void sendMsg(Text text) {
        if(mc.player != null) mc.player.sendMessage(text);
    }

    public void sendPrefixMsg(String text) {
        if(mc.player != null) mc.player.sendMessage(Text.of(translate(MarlowClient.getPrefix()) + translate(text)));
    }

    public String translate(String text) {
        return text.replace("&", paragraph);
    }


    public static void sendChatMessage(String msg) {
        mc.player.networkHandler.sendChatMessage(msg);
    }

    public static String sendErrorCommand(String usage){
        assert mc.player != null;
        mc.player.sendMessage(Text.of(MarlowClient.getCommandPrefix() + ColorUtils.red+" Incorrect Usage. Please use: " + usage));
        return null;
    }

    public static String sendSuccess(String message){
        assert mc.player != null;
        mc.player.sendMessage(Text.of(MarlowClient.getCommandPrefix() + ColorUtils.green+" Success! " + message));
        return null;
    }
}
