package net.mommymarlow.marlowclient.mixin;

import net.minecraft.entity.LivingEntity;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.render.SlowAttack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "getHandSwingDuration", at = @At("RETURN"), cancellable = true)
    public void getHandSwingDuration(CallbackInfoReturnable<Integer> cir) {
    if (ModuleManager.INSTANCE.getModuleByClass(SlowAttack.class).isEnabled()) cir.setReturnValue(12);
}

}
