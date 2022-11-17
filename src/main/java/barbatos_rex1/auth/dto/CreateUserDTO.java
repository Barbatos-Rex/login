package barbatos_rex1.auth.dto;

import java.util.List;

public class CreateUserDTO {
    public String email;
    public String username;
    public String password;
    public String confirmPassword;

    public List<RoleDTO> roles;

    public CreateUserDTO(String email, String username, String password, String confirmPassword, List<RoleDTO> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles=roles;
    }
}
