package xyz.andw.anode.listener;

import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mojang.authlib.GameProfile;

import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Entry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import xyz.andw.anode.Anode;
import xyz.andw.anode.command.GetClass;
import xyz.andw.anode.event.GameJoinS2CCallback;
import xyz.andw.anode.event.InventoryS2CCallback;
import xyz.andw.anode.event.PacketSendCallback;
import xyz.andw.anode.event.PlayerListS2CCallback;

public class ClientPacketListener {

	// https://github.com/Wynntils/Artemis/blob/1c10c4b97780ccb33e95c8bddb3d8c9280777744/common/src/main/java/com/wynntils/wynn/model/WorldStateManager.java#L156
	private static final Pattern WORLD_NAME = Pattern.compile("^§f {2}§lGlobal \\[(.*)\\]$");
	private static final UUID WORLD_NAME_UUID = UUID.fromString("16ff7452-714f-3752-b3cd-c3cb2068f6af");

    public static void init() {
        InventoryS2CCallback.EVENT.register((packet) -> {
			Stack<ArgumentRunnable> cbs = ListenerCallback.getCallbacks(InventoryS2CCallback.class);
			while (cbs != null && !cbs.empty()) {
				cbs.pop().run(packet);
			}
		});

		PlayerListS2CCallback.EVENT.register((packet) -> {
			List<Entry> entries = packet.getEntries();
			if (packet.getActions().contains(Action.UPDATE_DISPLAY_NAME)) {
				for (Entry e : entries) {
					GameProfile prof = e.profile();
					if (!prof.getId().equals(WORLD_NAME_UUID)) continue;

					String worldDisplayName = e.displayName().getString();
					Matcher matcher = WORLD_NAME.matcher(worldDisplayName);
					if (matcher.find()) {
						String worldName = matcher.group(1);
						if (worldName.equals(Anode.state.worldName)) break;

						Anode.state.worldName = worldName;
						Anode.state.isCheckingClass = true;
						Anode.state.inClassScreen = false;
						int[] retry = {1};
						Runnable retrySetClass = new Runnable() {
							public void run() {
								if (retry[0] == 11) {
									Anode.mc.player.sendMessage(Text.of("[Anode] Could not detect class after 10 attempts. Do /getclass to set class"), false);
									return;
								}

								if (Anode.state.className == null) {
									Anode.mc.player.sendMessage(Text.of("[Anode] Retrying class detection... #"+(retry[0]++)), false);
									Anode.scheduler.addJob(this, 1000);
								}
							}
						};

						Anode.scheduler.addJob(() -> {
							GetClass.setClassName();
							retrySetClass.run();
						}, 100);
					}
					break;
				}
			}
		});

		GameJoinS2CCallback.EVENT.register((packet) -> {
			// Stack<ArgumentRunnable> cbs = ListenerCallback.getCallbacks(GameJoinS2CCallback.class);
			// while (cbs != null && !cbs.empty()) {
			// 	cbs.pop().run(packet);
			// }

			// int[] retry = {1};
			// Runnable retrySetClass = new Runnable() {
			// 	public void run() {
			// 		if (retry[0] == 11) {
			// 			Anode.mc.player.sendMessage(Text.of("[Anode] Could not detect class after 10 attempts. Do /getclass to set class"), false);
			// 			return;
			// 		}

			// 		if (Anode.state.className == null) {
			// 			Anode.mc.player.sendMessage(Text.of("[Anode] Retrying class detection... #"+(++retry[0])), false);
			// 			Anode.scheduler.addJob(this, 1000);
			// 		}
			// 	}
			// };

			// Anode.scheduler.addJob(() -> {
			// 	GetClass.setClassName();
			// 	retrySetClass.run();
			// }, 10);
		});

		PacketSendCallback.EVENT.register((packet) -> {
			if (packet instanceof ClickSlotC2SPacket) {
				ClickSlotC2SPacket cPacket = (ClickSlotC2SPacket) packet;
				int slotIndex = cPacket.getSlot();
				if (!Anode.state.inClassScreen) return;
				if (slotIndex < 0 || slotIndex >= Anode.mc.player.currentScreenHandler.slots.size()) return;
				
				Slot selected = Anode.mc.player.currentScreenHandler.slots.get(slotIndex);
				ItemStack stck = selected.getStack(); // this is fine on artemis/wynntils
				if (stck.getItem() instanceof AirBlockItem) {
					stck = cPacket.getStack(); // this is needed on vanilla
					if (stck.getItem() instanceof AirBlockItem) return;
				}

				NbtList loreList = stck.getNbt().getCompound("display").getList("Lore", NbtList.STRING_TYPE);
					
				Pattern classPattern = Pattern.compile("\\{\"color\":\"gray\",\"text\":\"Class: \"\\},\\{\"color\":\"white\",\"text\":\"(.+?)\"\\}\\]");
				Matcher matcher = classPattern.matcher(loreList.asString());
				if (matcher.find()) {
					String className = matcher.group(1);
					Anode.state.className = className;
					Anode.mc.player.sendMessage(Text.of("[Anode] set class as " + Anode.state.className), false);
				} else {
					Anode.mc.player.sendMessage(Text.of("Could not find class name (failed regex? / misclicked)"), false);
				}

				Anode.state.inClassScreen = false;
			}
		});
    }
}
