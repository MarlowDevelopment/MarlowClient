package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.mommymarlow.marlowclient.event.EventManager;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.setting.NumberSetting;
import net.mommymarlow.marlowclient.utils.BlockUtils;
import net.mommymarlow.marlowclient.utils.InventoryUtils;

public class SwapCrystal extends Module {

    public final NumberSetting cooldown = new NumberSetting("Cooldown", 0, 20, 0, 0.1);
    public final BooleanSetting spoofHotbar = new BooleanSetting("SpoofHotbar", false);

    //private static long cooldown;

    public SwapCrystal() {
        super("SwapCrystal  ","  Auto swaps crystals" ,Category.COMBAT);
        addSettings(spoofHotbar, cooldown);
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
    private void onPacketSend(PacketSendEvent e){
            if (spoofHotbar.isEnabled()){
                if (e.getPacket() instanceof PlayerActionC2SPacket packet) {
                    if (cooldown.getValue() > System.currentTimeMillis()) return;
                    cooldown.setValue(System.currentTimeMillis() + (50 * 4));
                    if (packet.getAction() != PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) return;
                    BlockPos pos = packet.getPos();
                    if (!BlockUtils.isCrystallabe(pos)) return;
                    if (!InventoryUtils.hasItem(Items.END_CRYSTAL)) return;

                    if (InventoryUtils.nameContains("obsidian") || InventoryUtils.nameContains("totem") || InventoryUtils.nameContains("sword") || InventoryUtils.nameContains("anchor") || InventoryUtils.isHolding(Items.GLOWSTONE)) {
                        ItemStack item = mc.player.getStackInHand(mc.player.getActiveHand());
                        Item type = item.getItem();
                        e.setCancelled(true);
                        InventoryUtils.swap(Items.END_CRYSTAL);
                        BlockUtils.interact(pos,packet.getDirection());
                        InventoryUtils.swap(type);
                    }
                }
            }else {
                if (e.getPacket() instanceof PlayerActionC2SPacket packet) {
                    if (cooldown.getValue() > System.currentTimeMillis()) return;
                    cooldown.setValue(System.currentTimeMillis() + (50 * 4));
                    if (packet.getAction() != PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) return;
                    BlockPos pos = packet.getPos();
                    if (!BlockUtils.isCrystallabe(pos)) return;
                    if (!InventoryUtils.hasItem(Items.END_CRYSTAL)) return;

                    if (InventoryUtils.nameContains("obsidian") || InventoryUtils.nameContains("totem") || InventoryUtils.nameContains("sword") || InventoryUtils.nameContains("anchor") || InventoryUtils.isHolding(Items.GLOWSTONE)) {
                        e.setCancelled(true);
                        InventoryUtils.swap(Items.END_CRYSTAL);
                        BlockUtils.interact(pos,packet.getDirection());
                    }
                }
            }
    }
}