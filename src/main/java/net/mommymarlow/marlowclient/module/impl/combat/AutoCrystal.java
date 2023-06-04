package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.Listener;
import net.mommymarlow.marlowclient.event.impl.EventItemUse;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
import net.mommymarlow.marlowclient.event.impl.PlayerTickEvent;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.mommymarlow.marlowclient.module.ModuleManager;
import net.mommymarlow.marlowclient.scheduler.Randomizer;
import net.mommymarlow.marlowclient.scheduler.ScheduledTask;
import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.setting.ModeSetting;
import net.mommymarlow.marlowclient.setting.NumberSetting;
import net.mommymarlow.marlowclient.utils.*;
import org.lwjgl.glfw.GLFW;

public class AutoCrystal extends Module implements Listener {

    private final ModeSetting modes = new ModeSetting("Modes", "Attack", "Attack", "CW");
    public NumberSetting delay = new NumberSetting("Delay (Attack)", 0, 20, 0, 0.1);

    public BooleanSetting auto = new BooleanSetting("Auto (Attack)", false);
    public BooleanSetting breakAlso = new BooleanSetting("Break (Attack)", false);
    public NumberSetting placeInterval = new NumberSetting("PlaceInterval (CW)", 0, 20, 0, 0.1);
    public NumberSetting breakInterval = new NumberSetting("BreakInterval (CW)", 0, 20, 0, 0.1);
    public BooleanSetting activateOnRightClick = new BooleanSetting("OnRightClick (CW)", false);
    public BooleanSetting stopOnKill = new BooleanSetting("StopOnKill (CW)", false);


    private int crystalPlaceClock = 0;
    private int crystalBreakClock = 0;

    public AutoCrystal() {
        super("AutoCrystal  ", "  AutoCrystals", Category.COMBAT);
        addSettings(modes,auto,delay, breakAlso, placeInterval, breakInterval, activateOnRightClick, stopOnKill);
    }

    @Override
    public void onEnable() {
        EventManager.INSTANCE.register(this);
        system.addListener(this);
        super.onEnable();
        crystalBreakClock = 0;
        crystalPlaceClock = 0;
    }


    @Override
    public void onDisable() {
        EventManager.INSTANCE.unregister(this);
        system.removeListener(this);
        super.onDisable();
    }

    private boolean isDeadBodyNearby() {
        return mc.world.getPlayers().parallelStream()
                .filter(e -> mc.player != e)
                .filter(e -> e.squaredDistanceTo(mc.player) < 36)
                .anyMatch(LivingEntity::isDead);
    }

    double delayCrystal;


    @EventTarget
    private void onUpdate(EventUpdate e) {
        if (modes.isMode("CW")) {
            boolean dontPlaceCrystal = crystalPlaceClock != 0;
            boolean dontBreakCrystal = crystalBreakClock != 0;
            if (dontPlaceCrystal)
                crystalPlaceClock--;
            if (dontBreakCrystal)
                crystalBreakClock--;
            if (activateOnRightClick.isEnabled() && GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS)
                return;
            ItemStack mainHandStack = mc.player.getMainHandStack();
            if (!mainHandStack.isOf(Items.END_CRYSTAL))
                return;
            if (stopOnKill.isEnabled() && isDeadBodyNearby())
                return;

            if (mc.crosshairTarget instanceof EntityHitResult hit) {
                if (!dontBreakCrystal && hit.getEntity() instanceof EndCrystalEntity || !dontBreakCrystal && hit.getEntity() instanceof SlimeEntity) {
                    crystalBreakClock = breakInterval.getIntValue();
                    mc.interactionManager.attackEntity(mc.player, hit.getEntity());
                    mc.player.swingHand(Hand.MAIN_HAND);
                    CrystalDataTracker.INSTANCE.recordAttack(hit.getEntity());
                }
            }
            if (mc.crosshairTarget instanceof BlockHitResult hit) {
                BlockPos block = hit.getBlockPos();
                if (!dontPlaceCrystal && CrystalUtils.canPlaceCrystalServer(block)) {
                    crystalPlaceClock = placeInterval.getIntValue();
                    ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                    if (result.isAccepted() && result.shouldSwingHand())
                        mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }else if(modes.isMode("Attack")){
            if (!auto.isEnabled()) return;
            boolean dontCrystal = delayCrystal != 0;
            if (dontCrystal)
                crystalBreakClock--;
            if (!InventoryUtils.isHolding(Items.END_CRYSTAL)) return;
            if (GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_1) != GLFW.GLFW_PRESS) return;
            if (mc.crosshairTarget instanceof EntityHitResult hit) {
                if (!dontCrystal && hit.getEntity() instanceof EndCrystalEntity || !dontCrystal && hit.getEntity() instanceof SlimeEntity) {
                    delayCrystal = delay.getValue();
                    mc.interactionManager.attackEntity(mc.player, hit.getEntity());
                    mc.player.swingHand(Hand.MAIN_HAND);
                    CrystalDataTracker.INSTANCE.recordAttack(hit.getEntity());
                }
            }
            if (mc.crosshairTarget instanceof BlockHitResult hit) {
                BlockPos block = hit.getBlockPos();
                if (!dontCrystal && CrystalUtils.canPlaceCrystalServer(block)) {
                    delayCrystal = delay.getValue();
                    ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                    if (result.isAccepted() && result.shouldSwingHand())
                        mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }


    @EventTarget
    public void onItemUse(EventItemUse event) {
        if (modes.isMode("CW")) {
            ItemStack mainHandStack = mc.player.getMainHandStack();
            if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                BlockHitResult hit = (BlockHitResult) mc.crosshairTarget;
                if (mainHandStack.isOf(Items.END_CRYSTAL) && BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos()))
                    event.setCancelled(true);
            }
        }
    }

    @EventTarget
    private void onSendPacket(PacketSendEvent e) {
        if (modes.isMode("Attack")) {
            if (breakAlso.isEnabled()) {
                if (e.getPacket() instanceof PlayerActionC2SPacket) {
                    BlockPos pos = ((PlayerActionC2SPacket) e.getPacket()).getPos();
                    Direction direction = ((PlayerActionC2SPacket) e.getPacket()).getDirection();
                    if (!BlockUtils.isCrystallabe(pos)) return;
                    if (!InventoryUtils.isHolding(Items.END_CRYSTAL)) return;
                    InteractionUtils.doUse();
                    InteractionUtils.doAttack();
                }
            } else {
                if (e.getPacket() instanceof PlayerActionC2SPacket) {
                    BlockPos pos = ((PlayerActionC2SPacket) e.getPacket()).getPos();
                    Direction direction = ((PlayerActionC2SPacket) e.getPacket()).getDirection();
                    if (!BlockUtils.isCrystallabe(pos)) return;
                    if (!InventoryUtils.isHolding(Items.END_CRYSTAL)) return;
                    InteractionUtils.doUse();
                }
            }
        }
    }
}


