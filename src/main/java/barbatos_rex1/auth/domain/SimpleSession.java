package barbatos_rex1.auth.domain;

import barbatos_rex1.auth.domain.contract.*;
import barbatos_rex1.auth.dto.RoleDTO;
import barbatos_rex1.auth.dto.UserDTO;
import barbatos_rex1.auth.exception.AuthException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SimpleSession implements Session {

    public static final AuthException AUTH_EXCEPTION_INVALID_PASSWORD = new AuthException("Invalid password!");
    public static final AuthException AUTH_EXCEPTION_INVALID_USER = new AuthException("Invalid user!");
    public static final AuthException AUTH_EXCEPTION_INVALID_TOKEN = new AuthException("Invalid token!");
    private UserDTO user;
    private UserRepository repository;

    private PasswordManager passwordManager;

    public SimpleSession(UserDTO user, UserRepository repository, PasswordManager manager) {
        this.user = user;
        this.repository = repository;
        this.passwordManager = manager;
    }

    @Override
    public String username() {
        return user.username;
    }

    @Override
    public String email() {
        return user.email;
    }

    @Override
    public String userId() {
        return user.userId;
    }

    @Override
    public List<RoleDTO> roles() {
        return new LinkedList<>(user.roles);
    }

    @Override
    public boolean alterUsername(String newUsername, String password) throws AuthException {
        Optional<User> optUser = repository.findById(user.userId);
        if (optUser.isEmpty()) {
            throw AUTH_EXCEPTION_INVALID_USER;
        }

        if (!optUser.get().isValidPassword(password)) {
            throw AUTH_EXCEPTION_INVALID_PASSWORD;
        }
        user.username = newUsername;
        repository.updateUser(user);
        return true;
    }

    @Override
    public boolean alterUsername(String newUsername, AuthToken token) throws AuthException {
        Optional<User> optUser = repository.findByToken(token);
        if (optUser.isEmpty()) {
            throw AUTH_EXCEPTION_INVALID_USER;
        }

        if (!user.userId.equals(optUser.get().userId())) {
            throw AUTH_EXCEPTION_INVALID_TOKEN;
        }
        user.username = newUsername;
        repository.updateUser(user);
        return true;
    }

    @Override
    public boolean aleterRoles(List<RoleDTO> newRoles, String password) throws AuthException {
        Optional<User> optUser = repository.findById(user.userId);
        if (optUser.isEmpty()) {
            throw AUTH_EXCEPTION_INVALID_USER;
        }

        if (!optUser.get().isValidPassword(password)) {
            throw AUTH_EXCEPTION_INVALID_PASSWORD;
        }
        user.roles = newRoles;
        repository.updateUser(user);
        return true;
    }

    @Override
    public boolean aleterRoles(List<RoleDTO> newRoles, AuthToken token) throws AuthException {
        Optional<User> optUser = repository.findByToken(token);
        if (optUser.isEmpty()) {
            throw AUTH_EXCEPTION_INVALID_USER;
        }

        if (!user.userId.equals(optUser.get().userId())) {
            throw AUTH_EXCEPTION_INVALID_TOKEN;
        }
        user.roles = newRoles;
        repository.updateUser(user);
        return true;
    }

    @Override
    public boolean alterPassword(String newPassword, String password, String confirmPassword) throws AuthException {
        if (!password.equals(confirmPassword)) {
            throw new AuthException("Password differs from Confirm password!");
        }

        Optional<User> optUser = repository.findById(user.userId);
        if (optUser.isEmpty()) {
            throw AUTH_EXCEPTION_INVALID_USER;
        }

        if (!optUser.get().isValidPassword(password)) {
            throw AUTH_EXCEPTION_INVALID_PASSWORD;
        }
        user.hashedPassword = passwordManager.hashPassword(optUser.get().salt(), password);
        repository.updateUser(user);
        return true;
    }

    @Override
    public boolean alterPassword(String newPassword, AuthToken token) throws AuthException {
        Optional<User> optUser = repository.findByToken(token);
        if (optUser.isEmpty()) {
            throw AUTH_EXCEPTION_INVALID_USER;
        }

        if (!user.userId.equals(optUser.get().userId())) {
            throw AUTH_EXCEPTION_INVALID_TOKEN;
        }
        user.hashedPassword = passwordManager.hashPassword(optUser.get().salt(), newPassword);
        repository.updateUser(user);
        return true;
    }

    @Override
    public AuthToken refreshToken() {
        return repository.refreshToken(user.userId);
    }

    @Override
    public void logout() {
        this.user = null;
    }
}
