package net.mommymarlow.marlowclient.mixin;


import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.mommymarlow.marlowclient.event.impl.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mommymarlow.marlowclient.client.MarlowClient.system;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackEntity", at = @At("HEAD"),cancellable = true)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci){
        AttackEntityEvent event = new AttackEntityEvent(player, target);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }

}
