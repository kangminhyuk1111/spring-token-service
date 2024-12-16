package boot.tokentest.auth.service;

import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.fake.TestPasswordEncoder;
import boot.tokentest.auth.provider.KeyProvider;
import boot.tokentest.auth.fake.TestKeyProvider;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.member.domain.Member;
import boot.tokentest.member.repository.MemberRepositoryImpl;
import boot.tokentest.member.service.MemberService;
import boot.tokentest.member.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceTest {

    private final static String SECRET_KEY = "1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y7u8i9o0p";

    private final JwtRepository jwtRepository = new JwtRepository();
    private final KeyProvider keyProvider = new TestKeyProvider(SECRET_KEY);
    private final PasswordEncoder passwordEncoder = new TestPasswordEncoder();
    private final PasswordService passwordService = new PasswordService(passwordEncoder);
    private final MemberService memberService = new MemberService(new MemberRepositoryImpl(), passwordEncoder);
    private final AuthService authService = new AuthService(jwtRepository, passwordService, keyProvider, memberService);

    @Test
    void login() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto("kangminhyuk1111@naver.com", "12345678");
        final Member authSuccess = authService.login(loginRequestDto);

        assertThat(authSuccess).isNotNull();
    }
}