package com.reliance.businesseasy;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {

    private static Pattern pattern;
    private static Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String pancardPattern = "[a-zA-Z]{5}+[0-9]{4}+[a-zA-Z]{1}";


    public static boolean emailValidate(String email) {
        if (email != null && email.trim().length() > 0) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }
    }



    public static boolean isValidPAN(String pan) {
        if (!TextUtils.isEmpty(pan) && pan != null) {
            pattern = Pattern.compile(pancardPattern);
            matcher = pattern.matcher(pan);
            return matcher.matches();
        } else
            return false;
    }


}
