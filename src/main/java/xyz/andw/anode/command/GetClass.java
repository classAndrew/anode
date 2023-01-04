package xyz.andw.anode.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import xyz.andw.anode.Anode;
import xyz.andw.anode.event.InventoryS2CCallback;
import xyz.andw.anode.listener.ListenerCallback;

public class GetClass extends LiteralArgumentBuilder<FabricClientCommandSource> {

    // public static final int PLAYER_INFO_SLOT = 7;

    public GetClass(String literal) {
        super(literal);
        
        this.executes(context -> {
            GetClass.setClassName();
            Object o = Anode.mc.inGameHud;
            // Anode.mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(0, 0, 0, 0, null, null, null));
            return 1;
        });
    }

    public static void setClassName() {
        int preClassCheckSlot = Anode.mc.player.getInventory().selectedSlot;

        Anode.scheduler.addJob(() -> { 
            Anode.mc.player.getInventory().selectedSlot = 6;
            Anode.mc.interactionManager.interactItem(Anode.mc.player, Hand.MAIN_HAND);
            if (!(Anode.mc.player.getInventory().getMainHandStack().getItem() instanceof CompassItem)) {
                Anode.mc.player.getInventory().selectedSlot = preClassCheckSlot;
                Anode.mc.player.sendMessage(Text.of("[Anode] Cannot set class (in lobby?)"), false);
                return;
            }

            Anode.state.isCheckingClass = true;
            ListenerCallback.addCallback(InventoryS2CCallback.class, (arg1) -> {
                InventoryS2CPacket packet = (InventoryS2CPacket) arg1;
                ItemStack playerHead = packet.getContents().get(7);

                NbtList loreList = playerHead.getNbt().getCompound("display").getList("Lore", NbtList.STRING_TYPE);
                
                Pattern classPattern = Pattern.compile("\\[\\{\"color\":\"gray\",\"text\":\"Class: \"\\},\\{\"color\":\"white\",\"text\":\"(.+?)\"\\}\\]");
                Matcher matcher = classPattern.matcher(loreList.asString());
                if (matcher.find()) {
                    String className = matcher.group(1);
                    Anode.state.className = className;
                    Anode.mc.player.sendMessage(Text.of("[Anode] set class as " + Anode.state.className), false);
                } else {
                    Anode.mc.player.sendMessage(Text.of("Could not find class name (failed regex?)"), false);
                }

                Anode.mc.player.getInventory().selectedSlot = preClassCheckSlot;
            });
        }, 10);

    }
}
