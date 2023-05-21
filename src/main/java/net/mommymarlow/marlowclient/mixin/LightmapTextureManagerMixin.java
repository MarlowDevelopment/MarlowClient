package net.mommymarlow.marlowclient.mixin;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.world.dimension.DimensionType;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.render.FullBright;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for lighting and render for lighting
 */
@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin {

    @Inject(method = "getBrightness", at = @At("RETURN"), cancellable = true)
    private static void getBrightness(DimensionType type, int lightLevel, CallbackInfoReturnable<Float> cir) {
        if (ModuleManager.INSTANCE.getModuleByClass(FullBright.class).isEnabled()) cir.setReturnValue(15.0F);
    }
}
