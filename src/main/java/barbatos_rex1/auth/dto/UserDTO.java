package barbatos_rex1.auth.dto;

import java.util.List;

public class UserDTO {
    public String email;
    public String username;
    public String userId;
    public String hashedPassword;

    public List<RoleDTO> roles;


    public UserDTO(String email, String username, String userId, String hashedPassword, List<RoleDTO> roles) {
        this.email = email;
        this.username = username;
        this.userId = userId;
        this.hashedPassword = hashedPassword;
        this.roles = roles;
    }
}
