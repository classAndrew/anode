package xyz.andw.anode.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import xyz.andw.anode.Anode;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class AnodeMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		// Anode.LOGGER.info("Grabbing Regex Pattern for ");
	}
}
