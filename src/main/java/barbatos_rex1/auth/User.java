package barbatos_rex1.auth;

import barbatos_rex1.util.DTOAble;

import java.util.List;

public interface User extends DTOAble<UserDTO> {
    String email();
    String username();
    String userId();
    boolean isValidPassword(String password);
    List<Role> roles();
}
