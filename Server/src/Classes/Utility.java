package Classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Author: Steven Balagtas
 *
 * Class of static support methods used throughout the program.
 */

public class Utility {
    /**
     * Author: CAB302 Teaching Team
     *
     * Convert byte[] to string.
     */
    public static String bytesToString(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bytes) {
            stringBuffer.append(String.format("%02x", b & 0xFF));
        }
        return stringBuffer.toString();
    }

    /**
     * Author: CAB302 Teaching Team
     *
     * Generate a random session token.
     */
    public static String generateSessionToken() {
        // generate a random string to use as the session token
        Random rng = new Random();
        byte[] tokenBytes = new byte[32];
        rng.nextBytes(tokenBytes);
        return bytesToString(tokenBytes);
    }

    /**
     * Check if session token has expired.
     *
     * @return true if the session token has expired.
     */
    public static boolean hasTokenExpired(HashMap<String, ArrayList<String>> sessions, String token) {
        // get the expiry date and time of the token
        LocalDateTime tokenExpiryDateTime = LocalDateTime.parse(sessions.get(token).get(1));

        // check if the token has expired
        if (LocalDateTime.now().isAfter(tokenExpiryDateTime)) {
            return true;
        }
        return false;
    }
}