package xyz.andw.anode.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import xyz.andw.anode.event.ChatCallback;

// `- Captured "Aldorei Valley West Entrance"`
@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    public void addMessage(Text msg, CallbackInfo info) {
        ChatCallback.EVENT.invoker().trigger(msg.getString(), msg);
	}
}
