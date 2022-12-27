package xyz.andw.anode.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;

public interface SlotClickCallback {
    
    Event<SlotClickCallback> EVENT = EventFactory.createArrayBacked(SlotClickCallback.class,
        (listeners) -> (int slotIndex, int button, SlotActionType actionType, PlayerEntity player) -> {
            for (SlotClickCallback listener : listeners) {
                listener.trigger(slotIndex, button, actionType, player);
            }
    });

    void trigger(int slotIndex, int button, SlotActionType actionType, PlayerEntity player);
}
