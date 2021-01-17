package model;

import java.util.regex.Pattern;

public class RegString {
    public static void main(String[] args) {
        String sourceString = "действующие";
        String subString = "действующ";

        if (find(sourceString, subString)) {
            System.out.println("Find");
        } else {
            System.out.println("not find");
        }
    }

    private static boolean find(String sourceString, String subString) {
        if (sourceString == null || "".equalsIgnoreCase(sourceString)) return  false;
        return Pattern.compile("\\b" + subString.toLowerCase() + "*").matcher(sourceString.toLowerCase()).find();
    }
}
