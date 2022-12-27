package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;

public interface InventoryS2CCallback {
    
    Event<InventoryS2CCallback> EVENT = EventFactory.createArrayBacked(InventoryS2CCallback.class,
        (listeners) -> (packet) -> {
            for (InventoryS2CCallback listener : listeners) {
                listener.trigger(packet);
            }
    });

    void trigger(InventoryS2CPacket packet);
}
