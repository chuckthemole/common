package com.rumpus.common.util;

public class Random {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvxyz";
    private static final String DIGIT = "0123456789";

    // function to generate a random string of length n
    public static String alphaNumericUpper(int length) {

        // choose a Character random from this String
        String AlphaNumericString = (new StringBuilder()).append(UPPER).append(DIGIT).toString();

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
