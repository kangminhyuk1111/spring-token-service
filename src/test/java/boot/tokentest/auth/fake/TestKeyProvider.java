package boot.tokentest.auth.fake;

import boot.tokentest.auth.provider.KeyProvider;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class TestKeyProvider implements KeyProvider {

    private final String key;

    public TestKeyProvider(final String key) {
        this.key = key;
    }

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}
