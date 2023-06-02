package net.mommymarlow.marlowclient.client;

import net.mommymarlow.marlowclient.event.Event;
import net.mommymarlow.marlowclient.event.EventBus;
import net.mommymarlow.marlowclient.event.Listener;

import java.io.Serializable;

public class MarlowSystem implements Serializable {

    public static final MarlowSystem INSTANCE = new MarlowSystem();

    public final EventBus eventBus = EventBus.INSTANCE;


    public void addListener(Listener event) {
        eventBus.subscribe(event);
    }

    public void removeListener(Listener listener) {
        eventBus.unsubscribe(listener);
    }

}
