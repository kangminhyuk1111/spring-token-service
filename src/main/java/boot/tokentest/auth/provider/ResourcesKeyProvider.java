package boot.tokentest.auth.provider;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class ResourcesKeyProvider implements KeyProvider {

    @Value("${jwt.secret-key}")
    private String key;

    @Override
    public SecretKey getSecretKey() {
        if (key == null) {
            throw new IllegalStateException("Key is not initialized");
        }
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}
