package net.mommymarlow.marlowclient.event;

/**
 * Cancellable event
 */
public interface Cancellable {

    /**
     * Cancels the action
     * @param cancelled cancelled
     */
    void setCancelled(boolean cancelled);

    /**
     * Checks if cancelled
     * @return cancelled
     */
    boolean isCancelled();
}
