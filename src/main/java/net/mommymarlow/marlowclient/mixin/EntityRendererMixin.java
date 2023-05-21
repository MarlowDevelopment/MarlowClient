package net.mommymarlow.marlowclient.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.render.NickPing;
import net.mommymarlow.marlowclient.utils.ColorUtils;
import net.mommymarlow.marlowclient.utils.PlayerUtils;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {


    @Redirect(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I"))
    private int shouldDisplayPingInName(TextRenderer instance, Text text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumers, boolean seeThrough, int backgroundColor, int light, Entity entity) {
        if (entity.isPlayer() && ModuleManager.INSTANCE.getModuleByClass(NickPing.class).isEnabled()) {
            text = Text.literal(text.getString() + ColorUtils.red+" (" +ColorUtils.green+ PlayerUtils.getPing(entity) + "ms"+ColorUtils.red+")");
            x -= 15;
        }
        instance.draw(text, x, (float) y, color, false, matrix, vertexConsumers, seeThrough, backgroundColor, light);
        return color;
    }
}
