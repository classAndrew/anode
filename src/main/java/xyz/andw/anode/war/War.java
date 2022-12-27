package xyz.andw.anode.war;

import net.minecraft.entity.player.PlayerEntity;

public class War {
    
    public PlayerEntity[] teammates;
    public Tower tower;
    public PlayerEntity recorder;
    public long time;

    public War(PlayerEntity[] teammates, PlayerEntity recorder) {
        this.teammates = teammates;
        this.recorder = recorder;
        this.time = System.currentTimeMillis();
    }

    public String toString() {
        // TODO: serialize
        return null;
    }
}
