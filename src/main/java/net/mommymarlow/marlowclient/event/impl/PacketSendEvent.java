package net.mommymarlow.marlowclient.event.impl;

import net.minecraft.network.PacketCallbacks;
import net.mommymarlow.marlowclient.event.Cancellable;
import net.mommymarlow.marlowclient.event.Event;
import net.minecraft.network.Packet;

public class PacketSendEvent extends Event implements Cancellable {

    private final Packet<?> packet;
    private boolean cancelled;


    /**
     * Constructs a new client send packet event
     * @param packet packet sent
     */
    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
        this.cancelled = false;
    }

    public Packet<?> getPacket() {
        return packet;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
