package net.mommymarlow.marlowclient.utils;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;

import static net.mommymarlow.marlowclient.Marlow.mc;

public class PlayerUtils {

    public static int getPing(Entity player) {
        if (mc.getNetworkHandler() == null)
            return 0;
        final PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (playerListEntry == null)
            return 0;
        return playerListEntry.getLatency();
    }


    public static boolean isMoving() {
        return mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0;
    }
}
