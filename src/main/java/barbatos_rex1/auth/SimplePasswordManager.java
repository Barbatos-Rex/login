package barbatos_rex1.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SimplePasswordManager implements PasswordManager {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!#$%&/()=?@£§{[]}|+-;,:*";
    private MessageDigest digest;

    {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String genSalt(String userId) {
        Random saltGen = new Random(LongHasher.hash(userId));
        StringBuilder str = new StringBuilder();
        for (int i = 0;i<12;i++){
            str.append(CHARS.charAt(saltGen.nextInt(CHARS.length() - 1)));
        }
        return str.toString();
    }

    @Override
    public String generatePassword(int len) {
        Random saltGen = new Random(System.currentTimeMillis());
        StringBuilder str = new StringBuilder();
        for (int i = 0;i<12;i++){
            str.append(CHARS.charAt(saltGen.nextInt(CHARS.length() - 1)));
        }
        return str.toString();
    }

    @Override
    public String hashPassword(String salt, String password) {
        return new String(digest.digest((salt+password+salt).getBytes()));
    }
}
