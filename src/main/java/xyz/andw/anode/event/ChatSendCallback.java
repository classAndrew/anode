package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.text.Text;

public interface ChatSendCallback {
    
    Event<ChatSendCallback> EVENT = EventFactory.createArrayBacked(ChatSendCallback.class,
        (listeners) -> (message) -> {
            for (ChatSendCallback listener : listeners) {
                listener.trigger(message);
            }
    });

    void trigger(Text message);
}
