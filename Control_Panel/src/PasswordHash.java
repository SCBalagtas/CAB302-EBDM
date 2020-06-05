import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 * @author Felix Savins
 *
 * This class hashes a string passed to it. Used to hash the password before being sent to the server
 *
 */
public class PasswordHash {

    private String password;
    private String HexString;

    /**
     *
     * @param password String that needs to be hashed
     * @throws NoSuchAlgorithmException
     */

    public PasswordHash(String password) throws NoSuchAlgorithmException {
        this.password = password;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        this.HexString = hexString.toString();

    }

    /**
     *
     * @return hashed password
     */

    public String getHexString() {return HexString;}

}
