package barbatos_rex1.auth;

public interface PasswordManager {
    String genSalt(String userId);
    String generatePassword(int len);
    String hashPassword(String salt, String password);
}
