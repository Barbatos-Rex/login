package barbatos_rex1.auth.domain;

import barbatos_rex1.auth.domain.contract.*;
import barbatos_rex1.auth.dto.UserDTO;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {

    private Map<String, User> userMap;

    private Map<AuthToken, String> userIdMap;

    private PasswordManager manager;

    private Random random = new Random();

    private static final String HEX = "0123456789abcdef";


    public InMemoryUserRepository() {
        userMap = new HashMap<>();
        userIdMap = new HashMap<>();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Optional<User> findById(String userId) {
        if (!userMap.containsKey(userId)) {
            return Optional.empty();
        }
        return Optional.of(userMap.get(userId));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        for (User u : userMap.values()) {
            if (u.email().equals(email)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByToken(AuthToken token) {
        if (!userIdMap.containsKey(token)) {
            return Optional.empty();
        }
        if (!userMap.containsKey(userIdMap.get(token))) {
            return Optional.empty();
        }
        return Optional.of(userMap.get(userIdMap.get(token)));
    }

    @Override
    public Optional<User> updateUser(UserDTO dto) {

        if (!userMap.containsKey(dto.userId)) {
            return Optional.empty();
        }
        List<Role> roles = new LinkedList<>();
        dto.roles.forEach(d -> roles.add(new SimpleRole(d.roleId, d.roleDescription, d.accessLevel)));


        User u = new SimpleUser(dto.email, dto.username, dto.hashedPassword, dto.userId, roles, manager);
        userMap.replace(dto.userId, u);
        return Optional.of(u);

    }

    @Override
    public User save(User user) {
        if (userMap.containsKey(user.userId())) {
            return updateUser(user.toDto()).orElse(null);
        }
        generateToken(user.userId());
        userMap.put(user.userId(), user);
        return user;
    }

    @Override
    public AuthToken generateToken(String userId) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            sb.append(random.nextInt(HEX.length() - 1));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        AuthToken token = new AuthToken(sb.toString(), calendar.getTime());
        userIdMap.put(token, userId);
        return token;
    }

    @Override
    public AuthToken refreshToken(String userId) {
        return generateToken(userId);
    }

    @Override
    public void attachPasswordManager(PasswordManager manager) {
        this.manager = manager;
    }
}
