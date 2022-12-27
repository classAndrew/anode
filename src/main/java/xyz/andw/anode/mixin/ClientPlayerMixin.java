package xyz.andw.anode.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import xyz.andw.anode.Anode;
import xyz.andw.anode.event.ChatSendCallback;
import xyz.andw.anode.scheduler.Job;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin {

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void onTick(CallbackInfo info) {
        Job currJob = Anode.scheduler.peek();
        while (currJob != null && currJob.runAt <= System.currentTimeMillis()) {
            currJob.run();
            Anode.scheduler.remove();
            currJob = Anode.scheduler.peek();
        }
	}

    @Inject(at = @At("HEAD"), method = "sendChatMessage(Ljava/lang/String;)V")
    public void onSendChatMessage(String msg, CallbackInfo info) {
        ChatSendCallback.EVENT.invoker().trigger(msg);
    }
}
