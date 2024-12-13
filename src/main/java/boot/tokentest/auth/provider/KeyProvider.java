package boot.tokentest.auth.provider;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class KeyProvider {

    @Value("${jwt.secret-key}")
    private String key;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}
