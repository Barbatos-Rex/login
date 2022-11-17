package barbatos_rex1.auth.domain;

import barbatos_rex1.auth.domain.contract.PasswordManager;
import barbatos_rex1.auth.domain.contract.Role;
import barbatos_rex1.auth.domain.contract.User;
import barbatos_rex1.auth.dto.RoleDTO;
import barbatos_rex1.auth.dto.UserDTO;

import java.util.LinkedList;
import java.util.List;

public class SimpleUser implements User {

    private String email;
    private String username;
    private String hashedPassword;
    private String userId;
    private List<Role> roles;

    private String salt;

    public SimpleUser(String email, String username, String password, List<Role> roles, PasswordManager manager) {
        this.email = email;
        this.username = username;
        this.userId = manager.generateUserId();
        this.salt = manager.genSalt(userId);
        this.hashedPassword = manager.hashPassword(salt, password);
        this.roles = roles;
    }

    public SimpleUser(String email, String username, String hashedPassword, String userId, List<Role> roles, PasswordManager manager) {
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.userId = userId;
        this.roles = roles;
        this.salt = manager.genSalt(userId);
    }

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
    public String salt() {
        return salt;
    }

    @Override
    public UserDTO toDto() {
        List<RoleDTO> l = new LinkedList<>();
        roles.forEach(r -> l.add(r.toDto()));
        return new UserDTO(email, username, userId, hashedPassword, l);
    }
}
