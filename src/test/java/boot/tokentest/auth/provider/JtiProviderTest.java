package boot.tokentest.auth.provider;

import boot.tokentest.auth.fake.FakeJtiProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JtiProviderTest {

    private final JtiProvider jtiProvider = new FakeJtiProvider();

    @Test
    void jti_생성_검증() {
        // given & when
        final String jti = jtiProvider.generateJti();

        // then
        assertThat(jti).isNotEmpty();
    }
}
