package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.mommymarlow.marlowclient.Marlow;
import net.mommymarlow.marlowclient.client.MarlowClient;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.Listener;
import net.mommymarlow.marlowclient.event.impl.EventUpdate;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.setting.NumberSetting;
import net.mommymarlow.marlowclient.utils.FindItemResult;
import net.mommymarlow.marlowclient.utils.InventoryUtils;

public class TotemInvRestock extends Module implements Listener {


    //Settings
    public final BooleanSetting autoSwitch = new BooleanSetting("AutoSwitch", false);
    public final BooleanSetting checkHotbar = new BooleanSetting("CheckHotbar", false);
    private final BooleanSetting invOpen = new BooleanSetting("InvOpen", false);
    public final NumberSetting slot = new NumberSetting("Slot", 0, 8, 0, 1);


    int totems;

    //Register
    public TotemInvRestock() {
        super("TotemInvRestock  ", "  Restocks totems.", Category.COMBAT);
        addSettings(autoSwitch, checkHotbar, invOpen, slot);
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


    //Main function for every tick
    @EventTarget
    public void onTick(EventUpdate event)
    {
            if (!checkHotbar.isEnabled()){
                if  (!invOpen.isEnabled()) {
                    PlayerInventory inventory = mc.player.getInventory();
                    if (inventory.main.get(slot.getIntValue()).getItem()  != Items.TOTEM_OF_UNDYING)
                    {
                        int foundTotem = findTotemInInv();
                        if (foundTotem != -1){
                            mc.interactionManager.clickSlot(0, foundTotem, slot.getIntValue(), SlotActionType.SWAP, mc.player);
                            if (autoSwitch.isEnabled()){
                                if (inventory.getStack(slot.getIntValue()).isOf(Items.TOTEM_OF_UNDYING)){
                                    inventory.selectedSlot = slot.getIntValue();
                                }else {InventoryUtils.swap(Items.TOTEM_OF_UNDYING);}
                            }else {return;}
                        }
                    }else
                    {
                        return;
                    }
                }else if (invOpen.isEnabled() && mc.currentScreen instanceof InventoryScreen){
                    PlayerInventory inventory = mc.player.getInventory();
                    if (inventory.main.get(slot.getIntValue()).getItem()  != Items.TOTEM_OF_UNDYING)
                    {
                        int foundTotem = findTotemInInv();
                        if (foundTotem != -1){
                            mc.interactionManager.clickSlot(((InventoryScreen) mc.currentScreen).getScreenHandler().syncId, foundTotem, slot.getIntValue(), SlotActionType.SWAP, mc.player);
                            if (autoSwitch.isEnabled()){
                                if (inventory.getStack(slot.getIntValue()).isOf(Items.TOTEM_OF_UNDYING)){
                                    inventory.selectedSlot = slot.getIntValue();
                                }else {InventoryUtils.swap(Items.TOTEM_OF_UNDYING);}
                            }else {return;}
                        }
                    }else
                    {
                        return;
                    }
                }
            }else if(checkHotbar.isEnabled()){
                if  (!invOpen.isEnabled()) {
                    PlayerInventory inventory = mc.player.getInventory();
                    if (inventory.main.get(slot.getIntValue()).getItem()  != Items.TOTEM_OF_UNDYING)
                    {
                        int foundTotem1 = findTotemBoth();
                        if (foundTotem1 != -1){
                            mc.interactionManager.clickSlot(0, foundTotem1, slot.getIntValue(), SlotActionType.SWAP, mc.player);
                            if (autoSwitch.isEnabled()){
                                if (inventory.getStack(slot.getIntValue()).isOf(Items.TOTEM_OF_UNDYING)){
                                    inventory.selectedSlot = slot.getIntValue();
                                }else {InventoryUtils.swap(Items.TOTEM_OF_UNDYING);}
                            }else {return;}
                        }
                    }else
                    {
                        return;
                    }
                }else if (invOpen.isEnabled() && mc.currentScreen instanceof InventoryScreen){
                    PlayerInventory inventory = mc.player.getInventory();
                    if (inventory.main.get(slot.getIntValue()).getItem()  != Items.TOTEM_OF_UNDYING)
                    {

                        int foundTotem = findTotemBoth();
                        if (foundTotem != -1){
                            mc.interactionManager.clickSlot(((InventoryScreen) mc.currentScreen).getScreenHandler().syncId, foundTotem, slot.getIntValue(), SlotActionType.SWAP, mc.player);
                            if (autoSwitch.isEnabled()){
                                if (inventory.getStack(slot.getIntValue()).isOf(Items.TOTEM_OF_UNDYING)){
                                    inventory.selectedSlot = slot.getIntValue();
                                }else {InventoryUtils.swap(Items.TOTEM_OF_UNDYING);}
                            }else {return;}
                        }
                    }else
                    {
                        return;
                    }
                }
            }
    }



    public static int findTotemInInv()
    {
        PlayerInventory inventory = MarlowClient.mc.player.getInventory();
        for (int i = 9; i<36; i++){
            if (inventory.main.get(i).getItem() == Items.TOTEM_OF_UNDYING)
                return i;
        }
        return -1;
    }
    private int findTotemBoth()
    {
        PlayerInventory inventory = MarlowClient.mc.player.getInventory();
        totems = 0;
        int nextTotemSlot = -1;

        for(int slot = 0; slot <= 36; slot++)
        {
            if(!(inventory.getStack(slot).getItem() == Items.TOTEM_OF_UNDYING))
                continue;

            totems++;

            if(nextTotemSlot == -1)
                nextTotemSlot = slot < 9 ? slot + 36 : slot;
        }

        return nextTotemSlot;
    }


}
