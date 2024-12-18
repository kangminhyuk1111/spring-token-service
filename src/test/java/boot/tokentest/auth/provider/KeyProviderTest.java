package boot.tokentest.auth.provider;


import boot.tokentest.auth.fake.FakeKeyProvider;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;

class KeyProviderTest {

    private final KeyProvider keyProvider = new FakeKeyProvider();

    @Test
    void 시크릿_키_생성_테스트() {
        // given & when
        final SecretKey secretKey = keyProvider.getSecretKey();

        // then
        assertThat(secretKey).isNotNull();
    }
}
