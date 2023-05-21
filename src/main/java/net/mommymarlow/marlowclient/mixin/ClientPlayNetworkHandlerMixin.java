package net.mommymarlow.marlowclient.mixin;

import net.minecraft.text.Text;
import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.command.Command;
import net.mommymarlow.marlowclient.command.CommandManager;
import net.mommymarlow.marlowclient.event.impl.ChatSendEvent;
import net.mommymarlow.marlowclient.event.impl.CommandSuggestEvent;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mommymarlow.marlowclient.Marlow.mc;
import static net.mommymarlow.marlowclient.Marlow.system;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onCommandSuggestions", at =  @At("TAIL"), cancellable = true)
    public void onCmdSuggest(CommandSuggestionsS2CPacket packet, CallbackInfo ci) {
        CommandSuggestEvent event = new CommandSuggestEvent(packet);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }
/*
    OLD SEND PACKET METHOD

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    public void onSend(Packet<?> packet, CallbackInfo ci) {
        PacketSendEvent pse = new PacketSendEvent(packet);
        if(pse.isCancelled()) ci.cancel();
    }

 */


    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String content, CallbackInfo ci) {
        ChatSendEvent event = new ChatSendEvent(content);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }


    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String msg, CallbackInfo ci) {

        StringBuilder CMD = new StringBuilder();
        for (int i = 1; i < msg.toCharArray().length; ++i) {
            CMD.append(msg.toCharArray()[i]);
        }
        String[] args = CMD.toString().split(" ");

        if(msg.startsWith(Marlow.getCommandPrefix())) {
            for(Command command : CommandManager.INSTANCE.getCmds()) {
                if(args[0].equalsIgnoreCase(command.getName())) {
                    command.onCmd(msg, args);
                    ci.cancel();
                    break;
                }else { ci.cancel();}
            }
        }

        if(msg.startsWith(Marlow.getCommandPrefix()+"/")) {
            mc.player.sendMessage(Text.literal("scret menu"));
            ci.cancel();
        }
    }





}
