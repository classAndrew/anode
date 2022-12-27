package xyz.andw.anode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import xyz.andw.anode.command.GetClass;
import xyz.andw.anode.listener.ChatSendListener;
import xyz.andw.anode.listener.ClientPacketListener;
import xyz.andw.anode.listener.ScreenListener;
import xyz.andw.anode.listener.SlotClickListener;
import xyz.andw.anode.listener.WarChatListener;
import xyz.andw.anode.scheduler.Scheduler;

public class Anode implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Anode");
	public static MinecraftClient mc;
	public static State state = new State();
	public static Scheduler scheduler = new Scheduler();

	@Override
	public void onInitialize() {
		LOGGER.info("Setting mc...");
		mc = MinecraftClient.getInstance();

		WarChatListener.init();
		LOGGER.info("Initialized WarChatListener");

		ScreenListener.init();
		LOGGER.info("Initialized ScreenListener");

		ClientPacketListener.init();
		LOGGER.info("Initialized ClientPacketListener");

		ChatSendListener.init();
		LOGGER.info("Initialized ChatSendListener");

		SlotClickListener.init();
		LOGGER.info("Initialized SlotClickListener");

		ClientCommandManager.DISPATCHER.register(new GetClass("getclass"));
	}
}
