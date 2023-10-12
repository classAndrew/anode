package xyz.andw.anode.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import xyz.andw.anode.event.GameJoinS2CCallback;
import xyz.andw.anode.event.InventoryS2CCallback;
import xyz.andw.anode.event.PacketSendCallback;
import xyz.andw.anode.event.PlayerListS2CCallback;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {

    @Inject(at = @At("TAIL"), method = "sendPacket(Lnet/minecraft/network/packet/Packet;)V")
    public void onPacketSend(Packet packet, CallbackInfo info) {
        PacketSendCallback.EVENT.invoker().trigger(packet);   
	}
}
