package net.mommymarlow.marlowclient.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.mommymarlow.marlowclient.Marlow.mc;

public abstract class InventoryUtils {

    private static final Action ACTION = new Action();



    public static int previousSlot = -1;
    public static final int HOTBAR_START = 0;
    public static final int HOTBAR_END = 8;

    public static final int OFFHAND = 45;

    public static final int MAIN_START = 9;
    public static final int MAIN_END = 35;

    public static final int ARMOR_START = 36;
    public static final int ARMOR_END = 39;


    /**
     * Search of the item in a player's hot bar.
     * Scrolls to the item.
     *
     * @param item item to search
     *             Returns true or false
     */
    public static boolean useItem(Item item) {
        final PlayerInventory inventory = mc.player.getInventory();
        for (int i = 0; i <= 8; i++) {
            if (inventory.getStack(i).isOf(item)) {
                inventory.selectedSlot = i;
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if player has item in hotbar.
     *
     * @param item item to swap
     *             Returns true or false
     */
    public static boolean swap(Item item) {
        final PlayerInventory inv = mc.player.getInventory();
        for (int i = 0; i <= 8; i ++) {
            if (inv.getStack(i).isOf(item)) {
                inv.selectedSlot = i;
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if player has item in hotbar.
     *
     * @param item item to search
     *             Returns true or false
     */
    public static boolean hasItem(Item item) {
        final PlayerInventory inv = mc.player.getInventory();
        for (int i = 0; i <= 8; i ++) {
            if (inv.getStack(i).isOf(item)) return true;
        }
        return false;
    }


    public static boolean hasItemInInv(Predicate<Item> item) {
        final PlayerInventory inv = mc.player.getInventory();

        int count = 0;

        for (int i = 0; i < 36; i++) {
            final ItemStack itemStack = inv.getStack(i);
            if (item.test(itemStack.getItem())) {
                return true;
            }
        }

        return false;
    }


    public static FindItemResult find(Item... items) {
        return find(itemStack -> {
            for (Item item : items) {
                if (itemStack.getItem() == item) return true;
            }
            return false;
        });
    }

    public static FindItemResult find(Predicate<ItemStack> isGood) {
        return find(isGood, 0, mc.player.getInventory().size());
    }

    public static FindItemResult find(Predicate<ItemStack> isGood, int start, int end) {
        int slot = -1, count = 0;

        for (int i = start; i <= end; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (isGood.test(stack)) {
                if (slot == -1) slot = i;
                count += stack.getCount();
            }
        }

        return new FindItemResult(slot, count);
    }

    public static FindItemResult findFastestTool(BlockState state) {
        float bestScore = -1;
        int slot = -1;

        for (int i = 0; i < 9; i++) {
            float score = mc.player.getInventory().getStack(i).getMiningSpeedMultiplier(state);
            if (score > bestScore) {
                bestScore = score;
                slot = i;
            }
        }

        return new FindItemResult(slot, 1);
    }




    /**
     * If the player's held item matches the provided item type
     *
     * @param item item type
     * @return match
     */
    public static boolean isHolding(Item item) {
        return isHolding(item, Hand.MAIN_HAND);
    }


    /**
     * If the players held item in the specified hand matches the provided type
     *
     * @param item item type
     * @param hand hand to check
     * @return match
     */
    public static boolean isHolding(Item item, Hand hand) {
        return mc.player.getStackInHand(hand).isOf(item);
    }

    /**
     * If the player's held item's name contains the following string
     *
     * @param contains string to check
     * @return match
     */
    public static boolean nameContains(String contains) {
        return nameContains(contains, Hand.MAIN_HAND);
    }

    /**
     * If the player's held item in thee specified hand's name contains the following string
     *
     * @param contains string to check
     * @param hand     hand to check
     * @return match
     */
    public static boolean nameContains(String contains, Hand hand) {
        ItemStack item = mc.player.getStackInHand(hand);
        return item != null && item.getTranslationKey().toLowerCase().contains(contains.toLowerCase());
    }

    public static void forEachItem(Consumer<ItemStack> run) {
        for (int i = 0; i < 9; i++) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item == null) continue;
            if (item.isEmpty()) continue;
            run.accept(item);
        }
    }

    public static void forEachItem(BiConsumer<Integer, ItemStack> run) {
        for (int i = 0; i < 9; i++) {
            ItemStack item = mc.player.getInventory().getStack(i);
            if (item == null) continue;
            if (item.isEmpty()) continue;
            run.accept(i, item);
        }
    }

    public static ItemStack[] getContents() {
        PlayerInventory inv = mc.player.getInventory();
        ItemStack[] stacks = new ItemStack[]{};
        for (int i = 0; i < 9; i++) {
            stacks[i] = inv.getStack(i);
        }
        return stacks;
    }

    public static boolean isForClickCrystal() {
        return nameContains("sword")
                || isHolding(Items.END_CRYSTAL)
                || isHolding(Items.OBSIDIAN)
                || isHolding(Items.TOTEM_OF_UNDYING)
                || isHolding(Items.GLOWSTONE)
                || isHolding(Items.RESPAWN_ANCHOR);
    }

    public static void putInHotbar(Predicate<Item> item, int slot, Item item1) {
        if (!hasItemInInv(item)) {
            return;
        } else {
            if (!mc.player.getInventory().getStack(slot).isOf(item1)) {
                return;
            } else {

            }
        }
    }

    public static int indexToId(int i) {
        if (mc.player == null) return -1;
        ScreenHandler handler = mc.player.currentScreenHandler;

        if (handler instanceof PlayerScreenHandler) return survivalInventory(i);
        else if (handler instanceof GenericContainerScreenHandler) return genericContainer(i, ((GenericContainerScreenHandler) handler).getRows());
        else if (handler instanceof CraftingScreenHandler) return craftingTable(i);
        else if (handler instanceof FurnaceScreenHandler) return furnace(i);
        else if (handler instanceof BlastFurnaceScreenHandler) return furnace(i);
        else if (handler instanceof SmokerScreenHandler) return furnace(i);
        else if (handler instanceof Generic3x3ContainerScreenHandler) return generic3x3(i);
        else if (handler instanceof EnchantmentScreenHandler) return enchantmentTable(i);
        else if (handler instanceof BrewingStandScreenHandler) return brewingStand(i);
        else if (handler instanceof MerchantScreenHandler) return villager(i);
        else if (handler instanceof BeaconScreenHandler) return beacon(i);
        else if (handler instanceof AnvilScreenHandler) return anvil(i);
        else if (handler instanceof HopperScreenHandler) return hopper(i);
        else if (handler instanceof ShulkerBoxScreenHandler) return genericContainer(i, 3);
        else if (handler instanceof CartographyTableScreenHandler) return cartographyTable(i);
        else if (handler instanceof GrindstoneScreenHandler) return grindstone(i);
        else if (handler instanceof LecternScreenHandler) return lectern();
        else if (handler instanceof LoomScreenHandler) return loom(i);
        else if (handler instanceof StonecutterScreenHandler) return stonecutter(i);

        return -1;
    }

    public static Action click() {
        ACTION.type = SlotActionType.PICKUP;
        return ACTION;
    }


    public static Action move() {
        ACTION.type = SlotActionType.PICKUP;
        ACTION.two = true;
        return ACTION;
    }


    public static class Action {
        private SlotActionType type = null;
        private boolean two = false;
        private int from = -1;
        private int to = -1;
        private int data = 0;

        private boolean isRecursive = false;

        private Action() {}

        public Action fromId(int id) {
            from = id;
            return this;
        }

        public Action from(int index) {
            return fromId(InventoryUtils.indexToId(index));
        }

        public Action fromHotbar(int i) {
            return from(InventoryUtils.HOTBAR_START + i);
        }

        public Action fromOffhand() {
            return from(InventoryUtils.OFFHAND);
        }

        public Action fromMain(int i) {
            return from(InventoryUtils.MAIN_START + i);
        }

        public Action fromArmor(int i) {
            return from(InventoryUtils.ARMOR_START + (3 - i));
        }

        public void toId(int id) {
            to = id;
            run();
        }

        public void to(int index) {
            toId(InventoryUtils.indexToId(index));
        }

        public void toHotbar(int i) {
            to(InventoryUtils.HOTBAR_START + i);
        }

        public void toOffhand() {
            to(InventoryUtils.OFFHAND);
        }

        public void toMain(int i) {
            to(InventoryUtils.MAIN_START + i);
        }

        public void toArmor(int i) {
            to(InventoryUtils.ARMOR_START + (3 - i));
        }

        // Slot

        public void slotId(int id) {
            from = to = id;
            run();
        }

        public void slot(int index) {
            slotId(InventoryUtils.indexToId(index));
        }

        public void slotHotbar(int i) {
            slot(InventoryUtils.HOTBAR_START + i);
        }

        public void slotOffhand() {
            slot(InventoryUtils.OFFHAND);
        }

        public void slotMain(int i) {
            slot(InventoryUtils.MAIN_START + i);
        }

        public void slotArmor(int i) {
            slot(InventoryUtils.ARMOR_START + (3 - i));
        }

        // Other

        private void run() {
            boolean hadEmptyCursor = mc.player.currentScreenHandler.getCursorStack().isEmpty();

            if (type != null && from != -1 && to != -1) {
                click(from);
                if (two) click(to);
            }

            SlotActionType preType = type;
            boolean preTwo = two;
            int preFrom = from;
            int preTo = to;

            type = null;
            two = false;
            from = -1;
            to = -1;
            data = 0;

            if (!isRecursive && hadEmptyCursor && preType == SlotActionType.PICKUP && preTwo && (preFrom != -1 && preTo != -1) && !mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
                isRecursive = true;
                InventoryUtils.click().slotId(preFrom);
                isRecursive = false;
            }
        }

        private void click(int id) {
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, id, data, type, mc.player);
        }
    }


    private static int survivalInventory(int i) {
        if (isHotbar(i)) return 36 + i;
        if (isArmor(i)) return 5 + (i - 36);
        return i;
    }

    private static int genericContainer(int i, int rows) {
        if (isHotbar(i)) return (rows + 3) * 9 + i;
        if (isMain(i)) return rows * 9 + (i - 9);
        return -1;
    }

    private static int craftingTable(int i) {
        if (isHotbar(i)) return 37 + i;
        if (isMain(i)) return i + 1;
        return -1;
    }

    private static int furnace(int i) {
        if (isHotbar(i)) return 30 + i;
        if (isMain(i)) return 3 + (i - 9);
        return -1;
    }

    private static int generic3x3(int i) {
        if (isHotbar(i)) return 36 + i;
        if (isMain(i)) return i;
        return -1;
    }

    private static int enchantmentTable(int i) {
        if (isHotbar(i)) return 29 + i;
        if (isMain(i)) return 2 + (i - 9);
        return -1;
    }

    private static int brewingStand(int i) {
        if (isHotbar(i)) return 32 + i;
        if (isMain(i)) return 5 + (i - 9);
        return -1;
    }

    private static int villager(int i) {
        if (isHotbar(i)) return 30 + i;
        if (isMain(i)) return 3 + (i - 9);
        return -1;
    }

    private static int beacon(int i) {
        if (isHotbar(i)) return 28 + i;
        if (isMain(i)) return 1 + (i - 9);
        return -1;
    }

    private static int anvil(int i) {
        if (isHotbar(i)) return 30 + i;
        if (isMain(i)) return 3 + (i - 9);
        return -1;
    }

    private static int hopper(int i) {
        if (isHotbar(i)) return 32 + i;
        if (isMain(i)) return 5 + (i - 9);
        return -1;
    }

    private static int cartographyTable(int i) {
        if (isHotbar(i)) return 30 + i;
        if (isMain(i)) return 3 + (i - 9);
        return -1;
    }

    private static int grindstone(int i) {
        if (isHotbar(i)) return 30 + i;
        if (isMain(i)) return 3 + (i - 9);
        return -1;
    }

    private static int lectern() {
        return -1;
    }

    private static int loom(int i) {
        if (isHotbar(i)) return 31 + i;
        if (isMain(i)) return 4 + (i - 9);
        return -1;
    }

    private static int stonecutter(int i) {
        if (isHotbar(i)) return 29 + i;
        if (isMain(i)) return 2 + (i - 9);
        return -1;
    }

    public static boolean isHotbar(int i) {
        return i >= HOTBAR_START && i <= HOTBAR_END;
    }

    public static boolean isMain(int i) {
        return i >= MAIN_START && i <= MAIN_END;
    }

    public static boolean isArmor(int i) {
        return i >= ARMOR_START && i <= ARMOR_END;
    }

}
