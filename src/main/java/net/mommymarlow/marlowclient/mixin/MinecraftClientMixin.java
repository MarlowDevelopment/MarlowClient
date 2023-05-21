package net.mommymarlow.marlowclient.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.event.impl.EventItemUse;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.minecraft.client.MinecraftClient;
import net.mommymarlow.marlowclient.event.impl.SetScreenEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mommymarlow.marlowclient.Marlow.system;


@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow protected abstract String getWindowTitle();

    @Shadow public abstract void updateWindowTitle();

    @Shadow public abstract void setScreen(@Nullable Screen screen);


    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo ci) {
        EventUpdate event = new EventUpdate();
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }


    @Inject(method = "updateWindowTitle()V", at = @At("TAIL"))
    public void updateTitle(CallbackInfo ci){
        MinecraftClient.getInstance().getWindow().setTitle("Marlow Client - "+ Marlow.VERSION);
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
