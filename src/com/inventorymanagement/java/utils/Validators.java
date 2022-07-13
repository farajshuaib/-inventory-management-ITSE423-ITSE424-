
package com.inventorymanagement.java.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    public static Boolean isValidEmail(String value) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static Boolean isDouble(String value) {
        char[] letters = value.toCharArray();

        // check if empty
        if (letters.length < 1)
            return false;

        for (char letter : letters) {
            if (Double.isNaN(letter)) {
                return true;
            }
//            if (!Character.isDigit(letter)) {
//                return false;
//            }
        }
        return true;
    }

    public static Boolean isNumber(String value) {
        char[] letters = value.toCharArray();

        // check if empty
        if (letters.length < 1)
            return false;

        for (char letter : letters) {
            if (!Character.isDigit(letter)) {
                return false;
            }
        }
        return true;
    }
}