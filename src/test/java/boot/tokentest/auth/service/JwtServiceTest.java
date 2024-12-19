package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.fake.FakeJtiProvider;
import boot.tokentest.auth.fake.FakeKeyProvider;
import boot.tokentest.auth.provider.JjwtTokenProvider;
import boot.tokentest.auth.provider.JtiProvider;
import boot.tokentest.auth.provider.KeyProvider;
import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    private final KeyProvider keyProvider = new FakeKeyProvider();
    private final TokenProvider tokenProvider = new JjwtTokenProvider(keyProvider);
    private final JwtRepository jwtRepository = new JwtRepository();
    private final JtiProvider jtiProvider = new FakeJtiProvider();

    private final JwtService jwtService = new JwtService(tokenProvider, jwtRepository, jtiProvider);

    @Test
    void credential_생성_테스트() {
        // given
        final String email = "kangminhyuk1111@gmail.com";

        // when
        final AuthCredential authCredential = jwtService.generateAuthCredential(email);

        // then
        assertThat(authCredential).isNotNull();
        assertThat(authCredential.accessToken()).isNotEmpty();
        assertThat(authCredential.refreshToken()).isNotEmpty();
    }

    @Test
    void refreshToken을_통한_credential_재발급() {
        // given
        final String email = "kangminhyuk1111@gmail.com";
        final AuthCredential authCredential = jwtService.generateAuthCredential(email);
        final String refreshToken = authCredential.refreshToken();

        // when
        final AuthCredential refreshAuthCredential = jwtService.refreshToken(refreshToken);

        // then
        assertAll(
                () -> assertThat(refreshAuthCredential).isNotNull(),
                () -> assertThat(refreshAuthCredential.accessToken()).isEqualTo(refreshToken),
                () -> assertThat(refreshAuthCredential.refreshToken()).isEqualTo(refreshToken)
        );
    }

    @Test
    void 토큰_발급시_유효한_토큰임을_테스트() {
        // given
        final String email = "kangminhyuk1111@gmail.com";
        final AuthCredential authCredential = jwtService.generateAuthCredential(email);
        final String accessToken = authCredential.accessToken();

        // when & then
        assertThat(jwtService.isTokenActive(accessToken)).isTrue();
    }
}