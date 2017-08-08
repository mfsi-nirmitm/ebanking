/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bankapp.utils;

import java.util.regex.Pattern;

/**
 *
 * @author Santosh Kumar Singh
 */
public class CommonUtils {

    public static boolean isNumeric(String str) {
        try {
            Double i = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private static final Pattern rfc2822 = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

    public static boolean isValidEmail(String email) {
        if (!rfc2822.matcher(email)
                .matches()) {
            return false;
        }
        return true;
    }

    public static int InStr(String str, String str2) {
        if (str.contains(str2)) {
            return 0;
        }
        return 1;

    }
}
