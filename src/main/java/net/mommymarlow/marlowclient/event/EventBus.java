package net.mommymarlow.marlowclient.event;

import net.mommymarlow.marlowclient.event.Event;

import java.beans.EventHandler;
import java.lang.reflect.Method;
import java.util.*;

public class EventBus {

    public static final EventBus INSTANCE = new EventBus();
    private final Map<Class<? extends Listener>, Listener> subbedEvent;

    public EventBus() {
        this.subbedEvent = new HashMap<>();
    }

    public void subscribe(Listener event) {
        if (event == null) return;
        subbedEvent.remove(event.getClass());
        subbedEvent.put(event.getClass(),event);
    }

    public void unsubscribe(Listener listener) {
        if (listener == null) return;
        subbedEvent.remove(listener.getClass());
    }


    public <E extends Event> boolean pass(E event) {
        listeners().values().forEach(listener -> {
            List<Method> methods = Arrays
                    .stream(listener.getClass().getDeclaredMethods())
                    .filter(Objects::nonNull)
                    .filter(method -> method.isAnnotationPresent(EventTarget.class))
                    .sorted(Comparator.comparing(method -> ((Method) method).getAnnotation(EventTarget.class).priority()).reversed())
                    .toList();

            methods.forEach(method -> {
                this.tryInvoke(method, listener, event);
            });
        });

        return event instanceof Cancellable c && c.isCancelled();
    }
    private <E extends Event> void tryInvoke(Method method, Listener listener, E event) {
        try {
            if (!isValid(method, event)) return;
            method.setAccessible(true);
            method.invoke(listener, event);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private <E extends Event> boolean isValid(Method method, E event) {
        if (method == null || event == null) return false;
        if (!method.isAnnotationPresent(EventTarget.class)) return false;
        if (method.getReturnType() != void.class) return false;
        if (method.getParameterCount() != 1) return false;
        return method.getParameters()[0].getType() == event.getClass();
    }

    /**
     * Returns a map of registered events by the event bus
     * @return map
     */
    public HashMap<Class<? extends Listener>, Listener> listeners() {
        return new HashMap<>(subbedEvent);
    }

}
