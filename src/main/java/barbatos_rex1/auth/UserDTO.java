package barbatos_rex1.auth;

import barbatos_rex1.util.Mappable;

import java.util.List;

public class UserDTO implements Mappable<User> {
    public String email;
    public String username;
    public String userId;
    public String hashedPassword;

    public List<>


    public UserDTO(String email, String username, String userId, String hashedPassword) {
        this.email = email;
        this.username = username;
        this.userId = userId;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public User toDomain() {
        return ;
    }
}
