package barbatos_rex1.auth;

import java.util.List;

public class SimpleUser implements User{

    private String email;
    private String username;
    private String hashedPassword;
    private String userId;
    private List<Role> roles;

    private String salt;









    @Override
    public String email() {
        return email;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public boolean isValidPassword(String password) {
        return false;
    }

    @Override
    public List<Role> roles() {
        return roles;
    }

    @Override
    public UserDTO toDto() {
        return new UserDTO(email,username,userId,hashedPassword);
    }
}
