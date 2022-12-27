package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ChatSendCallback {
    
    Event<ChatSendCallback> EVENT = EventFactory.createArrayBacked(ChatSendCallback.class,
        (listeners) -> (message) -> {
            for (ChatSendCallback listener : listeners) {
                listener.trigger(message);
            }
    });

    void trigger(String message);
}
