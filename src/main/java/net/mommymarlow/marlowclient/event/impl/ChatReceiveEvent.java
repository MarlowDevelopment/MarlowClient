package net.mommymarlow.marlowclient.event.impl;

import net.mommymarlow.marlowclient.event.Event;

public class ChatReceiveEvent extends Event {

        private final String message;

        public ChatReceiveEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
}
