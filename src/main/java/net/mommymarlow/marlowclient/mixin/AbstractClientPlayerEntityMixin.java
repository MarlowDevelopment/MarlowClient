package net.mommymarlow.marlowclient.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.render.Cape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.mommymarlow.marlowclient.client.MarlowClient.mc;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {


    @Inject(method = "getCapeTexture", at = @At("HEAD"), cancellable = true)
    private void onGetCapeTexture(CallbackInfoReturnable<Identifier> info){
            if (ModuleManager.INSTANCE.getModuleByClass(Cape.class).isEnabled()) info.setReturnValue(getTexture((PlayerEntity) (Object) this));
            else return;
    }



    public Identifier getIdentifier(){
        Identifier iden = new Identifier("marlowclient", "capes/cape.png");
        return iden;
    }


    public Identifier getTexture(PlayerEntity player){
        if ((player == mc.player)){
            return getIdentifier();
        }else {
            return null;
        }
    }


}
