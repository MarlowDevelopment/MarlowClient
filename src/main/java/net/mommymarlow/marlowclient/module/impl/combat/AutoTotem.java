package net.mommymarlow.marlowclient.module.impl.combat;

import net.minecraft.item.Items;
import net.mommymarlow.marlowclient.event.EventTarget;
import net.mommymarlow.marlowclient.event.impl.PlayerTickEvent;
import net.mommymarlow.marlowclient.module.Category;
import net.mommymarlow.marlowclient.module.Module;
import net.mommymarlow.marlowclient.setting.BooleanSetting;
import net.mommymarlow.marlowclient.utils.FindItemResult;
import net.mommymarlow.marlowclient.utils.InventoryUtils;

public class AutoTotem extends Module {

    private BooleanSetting smart = new BooleanSetting("Smart", false);
    private BooleanSetting lock = new BooleanSetting("Lock", false);


    public AutoTotem() {
        super("AutoTotem  ", " auto puts totem in offhand", Category.COMBAT);
        addSettings(lock, smart);
    }

    int totems;
    private boolean locked;

    @EventTarget
    private void onUpdate(PlayerTickEvent e){
        FindItemResult result = InventoryUtils.find(Items.TOTEM_OF_UNDYING);
        totems = result.getCount();
        this.setDisplayName("AutoTotem " + "\2477" + totems);
        if (mc.player.getInventory().contains(Items.TOTEM_OF_UNDYING.getDefaultStack())) {
            if (this.lock.isEnabled()) this.locked = true;
            if (mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                if (!smart.isEnabled()) {
                    this.locked = true;
                    InventoryUtils.move().from(result.getSlot()).to(InventoryUtils.OFFHAND);
                } else {
                    if (mc.player.getHealth() < 15 || mc.player.isFallFlying() || mc.player.fallDistance > 6) {
                        this.locked = true;
                        InventoryUtils.move().from(result.getSlot()).to(InventoryUtils.OFFHAND);
                    } else {
                        this.locked = false;
                    }
                }
            }
            if (!smart.isEnabled()) {
                this.locked = true;
            } else {
                if (mc.player.getHealth() < 10 || mc.player.isFallFlying() || mc.player.fallDistance > 6) {
                    this.locked = true;
                } else {
                    this.locked = false;
                }
            }
        }
    }
}
