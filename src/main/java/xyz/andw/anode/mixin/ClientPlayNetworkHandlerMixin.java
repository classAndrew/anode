package xyz.andw.anode.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import xyz.andw.anode.event.GameJoinS2CCallback;
import xyz.andw.anode.event.InventoryS2CCallback;
import xyz.andw.anode.event.PacketSendCallback;
import xyz.andw.anode.event.PlayerListS2CCallback;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(at = @At("TAIL"), method = "onInventory(Lnet/minecraft/network/packet/s2c/play/InventoryS2CPacket;)V")
    public void onInventory(InventoryS2CPacket packet, CallbackInfo info) {
        InventoryS2CCallback.EVENT.invoker().trigger(packet);   
	}

    @Inject(at = @At("TAIL"), method = "onGameJoin(Lnet/minecraft/network/packet/s2c/play/GameJoinS2CPacket;)V")
    public void onGameJoin(GameJoinS2CPacket packet, CallbackInfo info) {
        GameJoinS2CCallback.EVENT.invoker().trigger(packet);   
	}

    @Inject(at = @At("TAIL"), method = "onPlayerList(Lnet/minecraft/network/packet/s2c/play/PlayerListS2CPacket;)V")
    public void onPlayerList(PlayerListS2CPacket packet, CallbackInfo info) {
        PlayerListS2CCallback.EVENT.invoker().trigger(packet);   
	}
}
