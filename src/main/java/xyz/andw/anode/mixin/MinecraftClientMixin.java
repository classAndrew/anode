package xyz.andw.anode.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import xyz.andw.anode.event.ScreenCallback;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("TAIL"), method = "setScreen(Lnet/minecraft/client/gui/screen/Screen;)V")
    public void onSetScreen(Screen screen, CallbackInfo info) {
        ScreenCallback.EVENT.invoker().trigger(screen);        
	}
}
