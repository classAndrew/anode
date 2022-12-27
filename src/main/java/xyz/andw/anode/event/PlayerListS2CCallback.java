package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;

public interface PlayerListS2CCallback {
    
    Event<PlayerListS2CCallback> EVENT = EventFactory.createArrayBacked(PlayerListS2CCallback.class,
        (listeners) -> (packet) -> {
            for (PlayerListS2CCallback listener : listeners) {
                listener.trigger(packet);
            }
    });

    void trigger(PlayerListS2CPacket packet);
}
