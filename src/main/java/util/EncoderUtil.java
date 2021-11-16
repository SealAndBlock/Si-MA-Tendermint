package util;

import java.util.Base64;

public class EncoderUtil {

    // Constructor.

    private EncoderUtil() {
    }

    // Methods.

    public static String encodeBase64(byte[] toEncode) {
        return Base64.getEncoder().encodeToString(toEncode);
    }

    public static byte[] decodeBase64(String toDecode) {
        return Base64.getDecoder().decode(toDecode);
    }

}
