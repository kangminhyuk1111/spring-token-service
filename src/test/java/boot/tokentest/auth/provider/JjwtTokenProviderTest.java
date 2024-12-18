package boot.tokentest.auth.provider;

import boot.tokentest.auth.fake.FakeJtiProvider;
import boot.tokentest.auth.fake.FakeKeyProvider;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class JjwtTokenProviderTest {

    private final TokenProvider tokenProvider = new JjwtTokenProvider(new FakeKeyProvider());
    private final JtiProvider jtiProvider = new FakeJtiProvider();

    @Test
    void 토큰_생성_테스트() {
        // given
        final String email = "kangminhyuk1111@gmail.com";
        final String jti = jtiProvider.generateJti();

        // when
        final String token = tokenProvider.createToken(email, jti);

        // then
        assertThat(token).isNotEmpty();
    }

    @Test
    void 토큰에서_Claims을_추출() {
        // given
        final String email = "kangminhyuk1111@gmail.com";
        final String jti = jtiProvider.generateJti();
        final String token = tokenProvider.createToken(email, jti);

        // when
        final Claims claims = tokenProvider.extractClaimsFromToken(token);

        // then
        assertThat(claims).isNotNull();
    }

    @Test
    void token에서_email을_추출() {
        // given
        final String email = "kangminhyuk1111@gmail.com";
        final String jti = jtiProvider.generateJti();
        final String token = tokenProvider.createToken(email, jti);

        // when
        final String extractedEmail = tokenProvider.extractEmailFromToken(token);

        // then
        assertThat(extractedEmail).isEqualTo(email);
    }

    @Test
    void token에서_jti를_추출() {
        // given
        final String email = "kangminhyuk1111@gmail.com";
        final String jti = jtiProvider.generateJti();
        final String token = tokenProvider.createToken(email, jti);

        // when
        final String extractedJti = tokenProvider.extractJtiFromToken(token);

        // then
        assertThat(extractedJti).isEqualTo(jti);
    }

    @Test
    void token에서_만료시간을_추출() {
        // given
        final String email = "kangminhyuk1111@gmail.com";
        final String jti = jtiProvider.generateJti();
        final String token = tokenProvider.createToken(email, jti);

        // when
        final Date date = tokenProvider.extractExpirationTimeFromToken(token);

        // then
        assertThat(date).isNotNull();
    }
}