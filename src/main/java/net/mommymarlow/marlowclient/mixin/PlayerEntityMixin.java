package net.mommymarlow.marlowclient.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.render.SlowAttack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "getAttackCooldownProgress", at = @At("RETURN"), cancellable = true)
    public void getAttackCooldownProgress(float baseTime, CallbackInfoReturnable<Float> cir) {

        if (ModuleManager.INSTANCE.getModuleByClass(SlowAttack.class).isEnabled()) cir.setReturnValue(1.0F);
    }

}
