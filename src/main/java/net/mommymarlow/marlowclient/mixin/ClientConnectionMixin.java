package net.mommymarlow.marlowclient.mixin;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.combat.CrystalOptimizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.Serializable;

import static net.mommymarlow.marlowclient.Marlow.mc;
import static net.mommymarlow.marlowclient.Marlow.system;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin implements Serializable {
    @Inject(method = "send(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("HEAD"), cancellable = true)
    private void onPacketSend(Packet<?> packet,PacketCallbacks callbacks, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();

    }
    /*

    @Inject(method = "send(Lnet/minecraft/network/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("HEAD"), cancellable = true)
    public void onPacketSendEvent(Packet<?> packet, PacketCallbacks callbacks, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        event.call();
        if (event.isCancelled()) ci.cancel();
    }

     */




}