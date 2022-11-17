package barbatos_rex1.auth.controller;

import barbatos_rex1.auth.domain.*;
import barbatos_rex1.auth.domain.contract.*;
import barbatos_rex1.auth.dto.CreateUserDTO;
import barbatos_rex1.auth.dto.LoginDTO;
import barbatos_rex1.auth.dto.UserDTO;
import barbatos_rex1.auth.exception.AuthException;
import barbatos_rex1.auth.middleware.LoginMiddleware;
import barbatos_rex1.auth.util.Validations;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LoginController {
    private final UserRepository repository;
    private final PasswordManager manager;

    private final LoginMiddleware middleware;

    public LoginController(UserRepository repository, PasswordManager manager) {
        this.repository = repository;
        this.manager = manager;
        this.repository.attachPasswordManager(this.manager);
        this.middleware=new NullMiddleware();
    }

    public LoginController(UserRepository repository, PasswordManager manager, LoginMiddleware middleware) {
        this.repository = repository;
        this.manager = manager;
        this.middleware = middleware;
    }

    public LoginDTO registerUser(CreateUserDTO createUser) throws AuthException {
        if (Validations.assertAnyNull(createUser.password, createUser.username, createUser.email, createUser.roles)) {
            throw new AuthException("Strings cannot be null");
        }
        if (!manager.validatePassword(createUser.password)) {
            throw new AuthException("Invalid password!");
        }
        if (!createUser.password.equals(createUser.confirmPassword)) {
            throw new AuthException("Passwords do not match!");
        }

        if (repository.findByEmail(createUser.email).isPresent()) {
            throw new AuthException("User with that email is already registered!");
        }

        List<Role> roles = new LinkedList<>();
        createUser.roles.forEach(dto -> roles.add(new SimpleRole(dto.roleId, dto.roleDescription, dto.accessLevel)));


        User u = new SimpleUser(createUser.email, createUser.username, createUser.password, roles, new SimplePasswordManager());

        u = repository.save(u);

        if (u == null) {
            throw new AuthException("Could not save user!");
        }

        middleware.onRegisterUser(u);

        UserDTO dto = u.toDto();


        return new LoginDTO(dto.email, dto.username, dto.userId, dto.roles, repository.generateToken(dto.userId));
    }

    public Session login(AuthToken token) throws AuthException {
        if (Validations.assertAnyNull(token)) {
            throw new AuthException("Null token");
        }

        Optional<User> optUser = repository.findByToken(token);

        if (token.getExpiration().before(new Date())) {
            throw new AuthException("Expired token!");
        }

        if (optUser.isEmpty()) {
            throw new AuthException("Invalid token!");
        }
        middleware.onLoginUser(optUser.get());

        return new SimpleSession(optUser.get().toDto(), repository, manager);


    }

    public Session login(String email, String password) throws AuthException {
        if (Validations.assertAnyNull(email, password)) {
            throw new AuthException("Neither email nor password can be null");
        }

        Optional<User> optUser = repository.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new AuthException("User not found");
        }

        if (!optUser.get().isValidPassword(password)) {
            throw new AuthException("Invalid password!");
        }

        middleware.onLoginUser(optUser.get());
        return new SimpleSession(optUser.get().toDto(), repository, manager);
    }


}
