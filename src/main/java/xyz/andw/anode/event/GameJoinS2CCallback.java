package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;

public interface GameJoinS2CCallback {
    
    Event<GameJoinS2CCallback> EVENT = EventFactory.createArrayBacked(GameJoinS2CCallback.class,
        (listeners) -> (packet) -> {
            for (GameJoinS2CCallback listener : listeners) {
                listener.trigger(packet);
            }
    });

    void trigger(GameJoinS2CPacket packet);
}
