package org.example.cosmeticwebpro.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Class declares exceptions
 */
public class ExceptionUtils {

    public static final String USER_SIGNUP_1 = "USER_SIGNUP_1";
    public static final String E_INTERNAL_SERVER = "E_INTERNAL_SERVER";
    public static Map<String, String> messages;

    static {
        messages = new HashMap<>();
        messages.put(ExceptionUtils.USER_SIGNUP_1, "Email or Username has been registered. Please enter Email or other Username.");
        messages.put(ExceptionUtils.E_INTERNAL_SERVER, "Server không phản hồi.");

    }
}
