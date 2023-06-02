package net.mommymarlow.marlowclient.mixin;

import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import net.mommymarlow.marlowclient.event.impl.HandSwingEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.mommymarlow.marlowclient.event.impl.PlayerTickEvent;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.module.ModuleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mommymarlow.marlowclient.client.MarlowClient.system;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(at = @At("HEAD"), method = "swingHand", cancellable = true)
    public void swingHand(Hand hand, CallbackInfo ci) {
        HandSwingEvent event = new HandSwingEvent(hand);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
            ordinal = 0), method = "tick()V", cancellable = true)
    private void onTick(CallbackInfo ci) {
        PlayerTickEvent event = new PlayerTickEvent();
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "move", at = @At(value = "HEAD"), cancellable = true)
    public void onMotion(MovementType type, Vec3d movement, CallbackInfo ci) {
        for (Module module : ModuleManager.INSTANCE.getEnabledModules()) {
            module.onMotion();
        }
    }
}
