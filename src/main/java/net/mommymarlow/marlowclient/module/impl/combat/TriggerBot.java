package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.AttackEntityEvent;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.mommymarlow.marlowclient.event.impl.PlayerTickEvent;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.module.impl.movement.Sprint;
import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.setting.NumberSetting;

public class TriggerBot extends Module {

    public final BooleanSetting hitboxes = new BooleanSetting("Hitboxes", false);
    public final NumberSetting hitboxrange = new NumberSetting("Hitbox Range", 0, 5, 0, 1);
    public final NumberSetting cooldown = new NumberSetting("Cooldown", 0, 1, 1, 0.1);

    @Override
    public void onEnable() {
        system.addListener(this);
        ModuleManager.INSTANCE.getModuleByClass(Sprint.class).setEnabled(true);
        super.onEnable();
    }


    @Override
    public void onDisable() {
        system.removeListener(this);
        ModuleManager.INSTANCE.getModuleByClass(Sprint.class).setEnabled(false);
        super.onDisable();
    }

    public TriggerBot() {
        super("TriggerBot  ", "  auto attacks player", Category.COMBAT);
        addSettings(cooldown);
    }

    @EventTarget
    private void onUpdate(PlayerTickEvent e) {
        if (mc.crosshairTarget instanceof EntityHitResult hit) {
            if (hit.getEntity() instanceof PlayerEntity) {
                if (mc.player.getAttackCooldownProgress(0) < cooldown.getValue())
                    return;
                assert mc.interactionManager != null;
                mc.interactionManager.attackEntity(mc.player, hit.getEntity());
                assert mc.player != null;
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }



}
