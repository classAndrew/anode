package xyz.andw.anode.listener;

import xyz.andw.anode.Anode;
import xyz.andw.anode.event.ChatSendCallback;

import java.util.stream.Collectors;

public class ChatSendListener {

    public static void init() {
        ChatSendCallback.EVENT.register((msg) -> {
			if (msg.withoutStyle().stream().map(String::valueOf).collect(Collectors.joining()).equals("/class")) {
                Anode.state.inClassScreen = true;
            }
		});
    }
}
