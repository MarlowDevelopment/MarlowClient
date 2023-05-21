package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.mommymarlow.marlowclient.event.Event;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.Listener;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
import net.mommymarlow.marlowclient.event.impl.PlayerTickEvent;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.setting.ModeSetting;
import net.mommymarlow.marlowclient.setting.NumberSetting;
import net.mommymarlow.marlowclient.utils.BlockUtils;
import net.mommymarlow.marlowclient.utils.InventoryUtils;

public class AutoAnchor extends Module implements Listener {


    public final ModeSetting modes = new ModeSetting("Mode", "Packet", "Marlow", "Packet");

    public AutoAnchor() {
        super("AutoAnchor  ", "  spam right click", Category.COMBAT);
        addSettings(modes);
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



    @EventTarget
    private void onPacketSend(PacketSendEvent e) {
            if (modes.isMode("Packet")){
                if (e.getPacket() instanceof PlayerInteractBlockC2SPacket packet) {
                    BlockPos pos = packet.getBlockHitResult().getBlockPos();
                    assert mc.player != null;
                    BlockState state = mc.player.getWorld().getBlockState(pos);
                    if (state == null) return;
                    if (InventoryUtils.isHolding(Items.RESPAWN_ANCHOR)) {
                        if (!state.isOf(Blocks.RESPAWN_ANCHOR)) InventoryUtils.swap(Items.GLOWSTONE);
                        else {
                            int charges = state.get(RespawnAnchorBlock.CHARGES);
                            if (charges >= 1) return;
                            e.setCancelled(true);
                            InventoryUtils.swap(Items.GLOWSTONE);
                            BlockUtils.interact(pos, packet.getBlockHitResult().getSide());
                        }
                    } else if (InventoryUtils.isHolding(Items.GLOWSTONE)) {
                        if (!state.isOf(Blocks.RESPAWN_ANCHOR)) {
                            e.setCancelled(true);
                            InventoryUtils.swap(Items.RESPAWN_ANCHOR);
                            BlockUtils.interact(pos, packet.getBlockHitResult().getSide());
                            InventoryUtils.swap(Items.GLOWSTONE);
                        } else InventoryUtils.swap(Items.RESPAWN_ANCHOR);
                    }
                }
            }
    }


    @EventTarget
    private void onPlayerUpdate(PlayerTickEvent e){
        if (modes.isMode("Marlow")){
            if (mc.crosshairTarget instanceof BlockHitResult hit)
            {
                BlockPos pos = hit.getBlockPos();
                if (BlockUtils.isAnchorCharged(pos))
                {
                    if (!mc.player.isHolding(Items.GLOWSTONE))
                    {
                        ActionResult actionResult = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
                        if (actionResult.isAccepted() && actionResult.shouldSwingHand())
                            mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        }
    }
}
