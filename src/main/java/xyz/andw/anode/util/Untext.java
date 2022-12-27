package xyz.andw.anode.util;

public class Untext {

    public static String unText(String s) {
        StringBuilder sb = new StringBuilder();
        char[] charS = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (charS[i] == '§') {
                i++; // skip the a in §a or whatever
                continue;
            }

            sb.append(charS[i]);
        }

        return sb.toString();
    }
}
