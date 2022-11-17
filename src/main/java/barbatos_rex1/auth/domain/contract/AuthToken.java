package barbatos_rex1.auth.domain.contract;

import java.util.Date;
import java.util.Objects;

public class AuthToken {
    private String key;
    private Date expiration;

    public AuthToken(String key, Date expiration) {
        this.key = key;
        this.expiration = expiration;
    }

    public String getKey() {
        return key;
    }

    public Date getExpiration() {
        return expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(getKey().toLowerCase(), authToken.getKey().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey().toLowerCase());
    }
}
