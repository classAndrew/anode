package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screen.Screen;

public interface ScreenCallback {
    
    Event<ScreenCallback> EVENT = EventFactory.createArrayBacked(ScreenCallback.class,
        (listeners) -> (screen) -> {
            for (ScreenCallback listener : listeners) {
                listener.trigger(screen);
            }
    });

    void trigger(Screen screen);
}
