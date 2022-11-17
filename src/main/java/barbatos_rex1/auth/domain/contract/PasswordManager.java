package barbatos_rex1.auth.domain.contract;

public interface PasswordManager {
    String genSalt(String userId);
    String generatePassword(int len);
    String hashPassword(String salt, String password);

    String generateUserId();

    boolean validatePassword(String password);

}
