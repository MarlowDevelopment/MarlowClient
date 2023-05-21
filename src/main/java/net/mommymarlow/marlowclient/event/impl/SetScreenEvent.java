package net.mommymarlow.marlowclient.event.impl;

import net.minecraft.client.gui.screen.Screen;
import net.mommymarlow.marlowclient.event.Event;

import static net.mommymarlow.marlowclient.Marlow.mc;

public class SetScreenEvent extends Event {

    private final Screen screen, previousScreen;
    private boolean cancelled;

    public SetScreenEvent(Screen screen) {
        this.previousScreen = mc.currentScreen;
        this.screen = screen;
        this.cancelled = false;
    }

    public Screen getScreen() {
        return screen;
    }

    public Screen getPreviousScreen() {
        return previousScreen;
    }
}
