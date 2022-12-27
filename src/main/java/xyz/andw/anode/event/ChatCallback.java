package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.text.Text;

public interface ChatCallback {
    
    Event<ChatCallback> EVENT = EventFactory.createArrayBacked(ChatCallback.class,
        (listeners) -> (msg, text) -> {
            for (ChatCallback listener : listeners) {
                listener.trigger(msg, text);
            }
    });

    void trigger(String msg, Text text);
}
