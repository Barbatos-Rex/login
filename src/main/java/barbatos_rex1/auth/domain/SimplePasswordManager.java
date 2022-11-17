package barbatos_rex1.auth.domain;

import barbatos_rex1.auth.domain.contract.PasswordManager;
import barbatos_rex1.auth.util.LongHasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SimplePasswordManager implements PasswordManager {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!#$%&/()=?@£§{[]}|+-;,:*";
    private static final String ID = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Random random = new Random();

    private MessageDigest digest;

    public SimplePasswordManager() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            digest=null;
        }
    }

    @Override
    public String genSalt(String userId) {
        Random saltGen = new Random(LongHasher.hash(userId));
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            str.append(CHARS.charAt(saltGen.nextInt(CHARS.length() - 1)));
        }
        return str.toString();
    }

    @Override
    public String generatePassword(int len) {
        Random saltGen = new Random(System.currentTimeMillis());
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            str.append(CHARS.charAt(saltGen.nextInt(CHARS.length() - 1)));
        }
        return str.toString();
    }

    @Override
    public String hashPassword(String salt, String password) {
        return new String(digest.digest((salt + password + salt).getBytes()));
    }

    @Override
    public String generateUserId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(idPart()).append("-");
        }
        sb.append(idPart());
        return sb.toString();
    }

    private String idPart() {
        StringBuilder sb = new StringBuilder();
        Random r = random;
        for (int i = 0; i < 8; i++) {
            sb.append(ID.charAt(r.nextInt(ID.length() - 1)));
        }
        return sb.toString();
    }

    @Override
    public boolean validatePassword(String password) {
        if (password.isBlank()) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        if (password.toLowerCase().equals(password)) {
            return false;
        }

        return password.matches(".*[0-9].*");
    }
}
