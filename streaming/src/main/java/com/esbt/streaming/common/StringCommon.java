package com.esbt.streaming.common;

import java.util.regex.Pattern;

public class StringCommon {
    public static final String EMPTY_STRING = "";

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_USERNAME_REGEX =
            Pattern.compile("^[a-zA-Z0-9._-]{3,30}$", Pattern.CASE_INSENSITIVE);
}
