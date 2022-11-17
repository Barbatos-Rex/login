package barbatos_rex1.auth.domain;

import barbatos_rex1.auth.domain.contract.User;
import barbatos_rex1.auth.middleware.LoginMiddleware;

public class NullMiddleware implements LoginMiddleware {
    @Override
    public LoginMiddleware next() {
        return null;
    }

    @Override
    public void onRegisterUser(User u) {
//Null Object
    }

    @Override
    public void onLoginUser(User u) {
//Null Object
    }
}
