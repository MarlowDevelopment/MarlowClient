package net.mommymarlow.marlowclient.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.mommymarlow.marlowclient.event.impl.EventEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mommymarlow.marlowclient.client.MarlowClient.mc;
import static net.mommymarlow.marlowclient.client.MarlowClient.system;
;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "addEntity", at = @At("RETURN"), cancellable = true)
    public void addEntity(int id, Entity entity, CallbackInfo ci) {
        if (entity instanceof PlayerEntity) {
            System.out.println("Sdfsdfs");
        }
        EventEntity.Spawn event = new EventEntity.Spawn(entity);
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "removeEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onRemoved()V"), cancellable = true)
    public void removeEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo ci) {
        if (mc.world.getEntityById(entityId) instanceof PlayerEntity) {
            System.out.println("sdfsdfs");
        }
        EventEntity.Remove event = new EventEntity.Remove(mc.world.getEntityById(entityId));
        system.eventBus.pass(event);
        if (event.isCancelled()) ci.cancel();
    }
}
