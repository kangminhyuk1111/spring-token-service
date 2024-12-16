package boot.tokentest.auth.provider;

import boot.tokentest.auth.fake.TestKeyProvider;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;

class KeyProviderTest {

    private final static String SECRET_KEY = "1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y7u8i9o0p";
    private final KeyProvider keyProvider = new TestKeyProvider(SECRET_KEY);

    @Test
    void 키_발급_테스트() {
        final SecretKey secretKey = keyProvider.getSecretKey();

        assertThat(secretKey).isNotNull();
    }
}