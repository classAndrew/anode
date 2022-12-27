package xyz.andw.anode.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import xyz.andw.anode.event.SlotClickCallback;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {

    @Inject(at = @At("HEAD"), method = "onSlotClick(IILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V")
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo info) {
        SlotClickCallback.EVENT.invoker().trigger(slotIndex, button, actionType, player);        
	}
}
