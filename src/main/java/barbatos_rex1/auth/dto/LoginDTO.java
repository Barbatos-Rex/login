package barbatos_rex1.auth.dto;

import barbatos_rex1.auth.domain.contract.AuthToken;

import java.util.List;

public class LoginDTO {

    public String email;
    public String username;
    public String userId;
    public List<RoleDTO> roles;
    public AuthToken token;

    public LoginDTO(String email, String username, String userId, List<RoleDTO> roles, AuthToken token) {
        this.email = email;
        this.username = username;
        this.userId = userId;
        this.roles = roles;
        this.token = token;
    }
}
