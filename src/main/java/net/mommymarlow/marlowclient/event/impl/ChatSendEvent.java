package net.mommymarlow.marlowclient.event.impl;

import net.mommymarlow.marlowclient.event.Event;

public class ChatSendEvent extends Event {
    private String message;
    private boolean cancelled;

    public ChatSendEvent(String message) {
        this.message = message;
        this.cancelled = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
