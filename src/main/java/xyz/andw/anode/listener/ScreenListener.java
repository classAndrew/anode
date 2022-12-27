package xyz.andw.anode.listener;

import xyz.andw.anode.Anode;
import xyz.andw.anode.event.ScreenCallback;

public class ScreenListener {
    
    public static void init() {
        ScreenCallback.EVENT.register((screen) -> {
			if (screen == null) return;

			if (screen.getTitle().getString().strip().equals("Character Info") && Anode.state.isCheckingClass) {
				screen.close();
				Anode.state.isCheckingClass = false;
			}
		});
    }
}
