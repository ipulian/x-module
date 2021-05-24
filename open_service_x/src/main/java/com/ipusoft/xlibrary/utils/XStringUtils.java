package com.ipusoft.xlibrary.utils;

import android.util.Base64;

/**
 * author : GWFan
 * time   : 5/20/21 10:22 AM
 * desc   :
 */

public class XStringUtils {

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String base64Encode2String(final byte[] input) {
        if (input == null || input.length == 0) return "";
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }
}
