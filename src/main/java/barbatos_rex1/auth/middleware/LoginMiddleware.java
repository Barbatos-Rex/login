package barbatos_rex1.auth.middleware;

import barbatos_rex1.auth.domain.contract.User;

public interface LoginMiddleware {

    LoginMiddleware next();

    void onRegisterUser(User u);

    void onLoginUser(User u);

}
