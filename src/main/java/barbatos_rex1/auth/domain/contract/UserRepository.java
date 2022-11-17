package barbatos_rex1.auth.domain.contract;

import barbatos_rex1.auth.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByToken(AuthToken token);

    Optional<User> updateUser(UserDTO dto);

    User save(User user);

    AuthToken generateToken(String userId);

    AuthToken refreshToken(String userId);

    void attachPasswordManager(PasswordManager manager);


}
