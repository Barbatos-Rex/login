package barbatos_rex1.auth.domain.contract;

import barbatos_rex1.auth.dto.RoleDTO;
import barbatos_rex1.auth.exception.AuthException;

import java.util.List;

public interface Session {

    String username();

    String email();

    String userId();

    List<RoleDTO> roles();

    boolean alterUsername(String newUsername, String password) throws AuthException;
    boolean alterUsername(String newUsername, AuthToken token) throws AuthException;

    boolean aleterRoles(List<RoleDTO> newRoles, String password) throws AuthException;
    boolean aleterRoles(List<RoleDTO> newRoles, AuthToken token) throws AuthException;

    boolean alterPassword(String newPassword, String password, String confirmPassword) throws AuthException;
    boolean alterPassword(String newPassword, AuthToken token) throws AuthException;

    AuthToken refreshToken();

    void logout();


}
