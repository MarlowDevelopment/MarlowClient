package net.mommymarlow.marlowclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.mommymarlow.marlowclient.event.impl.PlayerAttackEntityEvent;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.render.SlowAttack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.mommymarlow.marlowclient.client.MarlowClient.mc;
import static net.mommymarlow.marlowclient.client.MarlowClient.system;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "getAttackCooldownProgress", at = @At("RETURN"), cancellable = true)
    public void getAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> cir) {

        if (ModuleManager.INSTANCE.getModuleByClass(SlowAttack.class).isEnabled()) cir.setReturnValue(1.0F);
    }


    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void attack(Entity target, CallbackInfo ci) {
        PlayerAttackEntityEvent event = new PlayerAttackEntityEvent(mc.player, target, mc.crosshairTarget);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }

}
