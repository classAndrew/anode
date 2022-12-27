package xyz.andw.anode.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import xyz.andw.anode.Anode;
import xyz.andw.anode.event.SlotClickCallback;

public class SlotClickListener {

    public static void init() {
        SlotClickCallback.EVENT.register((slotIndex, button, actionType, player) -> {
			// moved to clientpacketlistener clickslotpacket
		});
    }
}
