package xyz.andw.anode.listener;

import xyz.andw.anode.event.SlotClickCallback;

public class SlotClickListener {

    public static void init() {
        SlotClickCallback.EVENT.register((slotIndex, button, actionType, player) -> {
			// moved to clientpacketlistener clickslotpacket
		});
    }
}
