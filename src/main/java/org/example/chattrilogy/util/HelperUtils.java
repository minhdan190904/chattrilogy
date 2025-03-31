package org.example.chattrilogy.util;

public class HelperUtils {
    public static boolean checkNumberPositive(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
