package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.PacketSendEvent;
import net.mommymarlow.marlowclient.event.impl.PlayerTickEvent;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.setting.NumberSetting;
import net.mommymarlow.marlowclient.utils.FindItemResult;
import net.mommymarlow.marlowclient.utils.InventoryUtils;
import org.lwjgl.glfw.GLFW;

public class SwapShield extends Module {
    public SwapShield() {
        super("SwapShield  ", "  swaps shield", Category.COMBAT);
    }
    @EventTarget
    private void packetSend(PacketSendEvent e){
        if (e.getPacket() instanceof PlayerInteractItemC2SPacket){
            if (InventoryUtils.nameContains("sword") || InventoryUtils.nameContains("axe")){
                if (InventoryUtils.hasItem(Items.SHIELD)){
                    InventoryUtils.swap(Items.SHIELD);
                }
            }
        }
    }
}
