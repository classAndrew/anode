package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.packet.Packet;


public interface PacketSendCallback {
    
    Event<PacketSendCallback> EVENT = EventFactory.createArrayBacked(PacketSendCallback.class,
        (listeners) -> (packet) -> {
            for (PacketSendCallback listener : listeners) {
                listener.trigger(packet);
            }
    });

    void trigger(Packet packet);
}
