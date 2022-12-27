package xyz.andw.anode.listener;

import java.util.HashMap;
import java.util.Stack;

public class ListenerCallback {

    private static HashMap<String, Stack<ArgumentRunnable>> callbacks = new HashMap<String, Stack<ArgumentRunnable>>();

    public static <T> Stack<ArgumentRunnable> getCallbacks(Class<T> cl) {
        Stack<ArgumentRunnable> stck = callbacks.get(cl.getName());
        if (stck == null) {
            stck = new Stack<ArgumentRunnable>();
            callbacks.put(cl.getName(), stck);
        }
        
        return stck;
    }

    public static <T> void addCallback(Class<T> cl, ArgumentRunnable runnable) {
        Stack<ArgumentRunnable> stck = callbacks.get(cl.getName());
        if (stck == null) {
            stck = new Stack<ArgumentRunnable>();
            callbacks.put(cl.getName(), stck);
        }

        stck.add(runnable);
    }
}
