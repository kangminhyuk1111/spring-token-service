package boot.tokentest.auth.fake;

import boot.tokentest.auth.provider.KeyProvider;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class FakeKeyProvider implements KeyProvider {

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor("1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y7u8i9o0p".getBytes());
    }
}
