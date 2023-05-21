package xyz.andw.anode.util;

public class UUID {
    public static String uuid36(String s) {
        return s.substring(0, 8) + '-' + s.substring(8, 12) + '-' + 
            s.substring(12, 16) + '-' + s.substring(16, 20) + '-' +
            s.substring(20, 32);
    }
}
