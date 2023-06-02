package net.mommymarlow.marlowclient.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.client.MarlowClient;
import net.mommymarlow.marlowclient.event.impl.EventItemUse;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.minecraft.client.MinecraftClient;
import net.mommymarlow.marlowclient.event.impl.PlayerAttackEntityEvent;
import net.mommymarlow.marlowclient.event.impl.SetScreenEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mommymarlow.marlowclient.client.MarlowClient.mc;
import static net.mommymarlow.marlowclient.client.MarlowClient.system;


@Mixin(MinecraftClient.class)
public  class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo ci) {
        EventUpdate event = new EventUpdate();
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }


    @Inject(method = "updateWindowTitle()V", at = @At("TAIL"))
    public void updateTitle(CallbackInfo ci){
        MinecraftClient.getInstance().getWindow().setTitle("Marlow Client - "+ MarlowClient.VERSION);
    }

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void set(Screen screen, CallbackInfo ci) {
        SetScreenEvent event = new SetScreenEvent(screen);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();

    }


    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void onDoItemUse(CallbackInfo ci){
        EventItemUse event = new EventItemUse();
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }


}
