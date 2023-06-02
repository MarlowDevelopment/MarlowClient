package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.Listener;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
import net.mommymarlow.marlowclient.event.impl.PlayerAttackEntityEvent;
import net.mommymarlow.marlowclient.mixin.ClientConnectionMixin;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.utils.InteractionUtils;

public class CrystalOptimizer extends Module implements Listener {


    public BooleanSetting marlow = new BooleanSetting("Marlow" ,false);
    public BooleanSetting client = new BooleanSetting("Client" ,false);


    public CrystalOptimizer() {
        super("CrystalOptimizer  ", "  Optimizes Crystals", Category.COMBAT);
        addSettings(marlow, client);
    }


    @Override
    public void onEnable() {
        system.addListener(this);
        super.onEnable();
    }


    @Override
    public void onDisable() {
        system.removeListener(this);
        super.onDisable();
    }

    private boolean isTool(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ToolItem) || itemStack.getItem() instanceof HoeItem) {
            return false;
        }
        ToolMaterial material = ((ToolItem) itemStack.getItem()).getMaterial();
        return material == ToolMaterials.DIAMOND || material == ToolMaterials.NETHERITE;
    }

    @EventTarget
    public void onPacket(PacketSendEvent e){
        if (marlow.isEnabled()){
            if (e.getPacket() instanceof PlayerInteractEntityC2SPacket interactPacket) {
                interactPacket.handle(new PlayerInteractEntityC2SPacket.Handler() {
                    @Override
                    public void interact(Hand hand) {
                        // N/A
                    }

                    @Override
                    public void interactAt(Hand hand, Vec3d pos) {
                        // N/A
                    }

                    @Override
                    public void attack() {
                        EntityHitResult entityHitResult;
                        Entity entity;
                        HitResult hitResult = mc.crosshairTarget;
                        if (hitResult == null) {
                            return;
                        }
                        if (hitResult.getType() == HitResult.Type.ENTITY && (entity = (entityHitResult = (EntityHitResult) hitResult).getEntity()) instanceof EndCrystalEntity) {
                            StatusEffectInstance weakness = mc.player.getStatusEffect(StatusEffects.WEAKNESS);
                            StatusEffectInstance strength = mc.player.getStatusEffect(StatusEffects.STRENGTH);
                            if (!(weakness == null || strength != null && strength.getAmplifier() > weakness.getAmplifier() || isTool(mc.player.getMainHandStack()))) {
                                return;
                            }
                            entity.kill();
                            entity.setRemoved(Entity.RemovalReason.KILLED);
                            entity.onRemoved();
                        }
                    }
                });


            }
        }
    }

    @EventTarget
    private void onPacketSend(PlayerAttackEntityEvent e) {
        final Entity ent = e.getEntity();

        if (ent == null) return;
        if (mc.player == null) return;
        if (mc.player.getWorld() == null) return;
        if (mc.isInSingleplayer()) return;
        if (!InteractionUtils.canBreakCrystals()) return;

        if (ent instanceof EndCrystalEntity crystal) {
            crystal.kill();
            crystal.remove(Entity.RemovalReason.KILLED);
            crystal.onRemoved();
        }
    }
}
