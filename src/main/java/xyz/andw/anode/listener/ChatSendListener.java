package xyz.andw.anode.listener;

import xyz.andw.anode.Anode;
import xyz.andw.anode.event.ChatSendCallback;

public class ChatSendListener {

    public static void init() {
        ChatSendCallback.EVENT.register((msg) -> {
			if (msg.getString().strip().equals("/class")) {
                Anode.state.inClassScreen = true;
            }
		});
    }
}
