package xyz.andw.anode;

import xyz.andw.anode.war.War;

public class State {
    public final Object lock = new Object();
    public War currentWar;
    public String className;
    public long lastWarTrackTime = 0;

    // class checking
    public boolean isCheckingClass = false;
    public boolean inClassScreen = false;
    public String worldName = "";
}
