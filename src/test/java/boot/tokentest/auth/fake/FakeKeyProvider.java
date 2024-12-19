package boot.tokentest.auth.fake;

import boot.tokentest.auth.provider.KeyProvider;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

public class FakeKeyProvider implements KeyProvider {

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor("1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y7u8i9o0p".getBytes());
    }

    @Override
    public Date getExpiration() {
        return new Date(System.currentTimeMillis() + Duration.ofDays(1).toMillis());
    }
}
