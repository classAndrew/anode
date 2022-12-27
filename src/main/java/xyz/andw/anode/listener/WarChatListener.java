package xyz.andw.anode.listener;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.realms.Request;
import net.minecraft.text.Text;
import xyz.andw.anode.Anode;
import xyz.andw.anode.event.ChatCallback;
import xyz.andw.anode.mixin.BossBarHudAccessor;
import xyz.andw.anode.util.Untext;
import xyz.andw.anode.war.Tower;
import xyz.andw.anode.war.War;

public class WarChatListener {
	static final Text WAR_TRACKED_MSG = Text.of("[Anode] Tracked attempt");
	static final Text WAR_SUCCESS_MSG = Text.of("[Anode] War Succeeded");
	static final Text WAR_FAILED_OLD_MSG = Text.of("[Anode] Clearing failed war (10 minutes ago)");
	static final Text WAR_FAILED_MSG = Text.of("[Anode] Clearing previous failed war");
    
    public static void init() {
        ChatCallback.EVENT.register((msg, text) -> {
			String strippedMsg = msg.strip();
			// Anode.LOGGER.info("the text: " + "'"+Untext.unText("§3[SDU] §bLight Forest West Mid Tower§7 - §4❤ 300000§7 (§610.0%§7) - §c☠ 1000-1500§7 (§b0.5x§7)")+"'");
			
			if (strippedMsg.startsWith("<Aezuh> hi")) {
				Anode.scheduler.addJob(() -> { Anode.mc.player.sendMessage(WAR_TRACKED_MSG, false); }, 1000*5);
			}

			Map<UUID, ClientBossBar> bars = ((BossBarHudAccessor) Anode.mc.inGameHud.getBossBarHud()).getBossBars();
			final long duplicateCooldown = 5000; // some clients receive the message twice.
			long now = System.currentTimeMillis();
			if (strippedMsg.contains("§3[WAR§3] The battle has begun!") && now - Anode.state.lastWarTrackTime > duplicateCooldown) {
				Anode.state.lastWarTrackTime = now;
				
				if (Anode.state.currentWar != null) {
					Anode.mc.player.sendMessage(WAR_FAILED_MSG, false);
					Anode.state.currentWar = null;
				}

				Iterator<UUID> uuidIter = bars.keySet().iterator(); 

				UUID bossbarID = uuidIter.next(); // there should always be two
				String barString = Untext.unText(bars.get(bossbarID).getName().getString());
				
				if (!barString.contains("❤")) { // it's the guild level thing, so the next bar should have a heart in it
					bossbarID = uuidIter.next(); 
					barString = Untext.unText(bars.get(bossbarID).getName().getString()); // gotta be one of the two
				}

				// §3[SDU] §bLight Forest West Mid Tower§7 - §4❤ 300000§7 (§610.0%§7) - §c☠ 1000-1500§7 (§b0.5x§7)
				// [SDU] Light Forest West Mid Tower - ❤ 300000 (10.0%) - ☠ 1000-1500 (0.5x)
				// §7Lv. 93§f - §l§bTitans Valor§f - §757% XP
				// Lv. 93 - Titans Valor - 57% XP
				// String barString = Untext.unText("§3[SDU] §bLight Forest West Mid Tower§7 - §4❤ 300000§7 (§610.0%§7) - §c☠ 1000-1500§7 (§b0.5x§7)");
				Anode.state.currentWar = new War(null, null);
				Anode.state.currentWar.tower = Tower.fromBossString(barString);
				
				// schedule to remove war entry in 10 minutes, if another war won't be started and this war was failed
				Anode.scheduler.addJob(() -> { 
					if (Anode.state.currentWar != null)
						Anode.mc.player.sendMessage(WAR_FAILED_OLD_MSG, false);
					Anode.state.currentWar = null; 
				}, 1000*60*10);

				Anode.scheduler.addJobAsync(() -> { 
					// make a request to christmas bear api
					Tower tower = Anode.state.currentWar.tower;
					String towerString = String.format("{\"owner\": \"%s\", \"territory\": \"%s\", \"health\": %d, \"defense\": %f, \"damage\": \"%s\", \"attackSpeed\": %f}",
						tower.guildTag, tower.terrName.replace(" Tower", ""), tower.hp, tower.def, String.format("%d-%d", tower.dmgLo, tower.dmgHi), tower.atkSpeed);
					String jsonString = String.format("{\"class_\": \"%s\", \"name\": \"%s\",  \"uuid\": \"%s\", \"tower\": %s}", Anode.state.className, Anode.mc.player.getName().getString(), Anode.mc.player.getUuid().toString(), towerString);
					Request r = Request.post("http://38.242.159.42:6969/postwar", jsonString, 5000, 5000);
					Anode.LOGGER.info("POST WAR, FROM SERVER: {}", r.text());
					Anode.mc.player.sendMessage(WAR_TRACKED_MSG, false);
				}, 10);
			}

			else if (strippedMsg.startsWith("- Captured")) {
				Anode.mc.player.sendMessage(WAR_SUCCESS_MSG, false);
				Anode.state.currentWar = null;
			}
		});
    }
}
