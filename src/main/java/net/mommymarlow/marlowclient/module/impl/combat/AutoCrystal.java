package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.Listener;
import net.mommymarlow.marlowclient.event.impl.EventItemUse;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
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

    private final ModeSetting modes = new ModeSetting("Modes", "ClickCrystal", "ClickCrystal", "CwCrystal");

    public NumberSetting placeInterval = new NumberSetting("PlaceInterval (Cw)", 0, 20, 0, 0.1);
    public NumberSetting breakInterval = new NumberSetting("BreakInterval (Cw)", 0, 20, 0, 0.1);
    public BooleanSetting activateOnRightClick = new BooleanSetting("OnRightClick (Cw)", false);
    public BooleanSetting stopOnKill = new BooleanSetting("StopOnKill (Cw)", false);


    private int crystalPlaceClock = 0;
    private int crystalBreakClock = 0;

    public AutoCrystal() {
        super("AutoCrystal  ", "  AutoCrystals",Category.COMBAT);
        addSettings(modes, placeInterval, breakInterval, activateOnRightClick, stopOnKill);
    }

    @Override
    public void onEnable() {
        system.addListener(this);
        super.onEnable();
        crystalBreakClock = 0;
        crystalPlaceClock = 0;
    }


    @Override
    public void onDisable() {
        system.removeListener(this);
        super.onDisable();
    }

    private boolean isDeadBodyNearby()
    {
        return mc.world.getPlayers().parallelStream()
                .filter(e -> mc.player != e)
                .filter(e -> e.squaredDistanceTo(mc.player) < 36)
                .anyMatch(LivingEntity::isDead);
    }


    @EventTarget
    private void onUpdate(EventUpdate e){
        if (modes.isMode("CwCrystal")){
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

            if (mc.crosshairTarget instanceof EntityHitResult hit)
            {
                if (!dontBreakCrystal && hit.getEntity() instanceof EndCrystalEntity crystal)
                {
                    crystalBreakClock = breakInterval.getIntValue();
                    mc.interactionManager.attackEntity(mc.player, crystal);
                    mc.player.swingHand(Hand.MAIN_HAND);
                    CrystalDataTracker.INSTANCE.recordAttack(crystal);
                }
            }
            if (mc.crosshairTarget instanceof BlockHitResult hit)
            {
                BlockPos block = hit.getBlockPos();
                if (!dontPlaceCrystal && CrystalUtils.canPlaceCrystalServer(block))
                {
                    crystalPlaceClock = placeInterval.getIntValue();
                    ActionResult result = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                    if (result.isAccepted() && result.shouldSwingHand())
                        mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }


    @EventTarget
    public void onItemUse(EventItemUse event)
    {
        if (modes.isMode("CwCrystal")){
            ItemStack mainHandStack = mc.player.getMainHandStack();
            if (mc.crosshairTarget.getType() == HitResult.Type.BLOCK)
            {
                BlockHitResult hit = (BlockHitResult) mc.crosshairTarget;
                if (mainHandStack.isOf(Items.END_CRYSTAL) && BlockUtils.isBlock(Blocks.OBSIDIAN, hit.getBlockPos()))
                    event.setCancelled(true);
            }
        }
    }


    @EventTarget
    private void onSendPacket(PacketSendEvent e) {
        if (modes.isMode("ClickCrystal")){
            if (e.getPacket() instanceof PlayerActionC2SPacket packet) {
                if (packet.getAction() != PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) return;
                final BlockPos pos = packet.getPos();
                if (!BlockUtils.isCrystallabe(pos)) return;

                if (InventoryUtils.isHolding(Items.END_CRYSTAL)) {
                    e.setCancelled(true);
                    BlockUtils.interact(pos,packet.getDirection());

                }
            }
        }
    }
}


