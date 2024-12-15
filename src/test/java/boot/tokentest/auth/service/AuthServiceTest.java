package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredentials;
import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthServiceTest {

    private final AuthService authService;

    AuthServiceTest(final AuthService authService) {
        this.authService = authService;
    }

    @Test
    public void login() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto("kang", "1234");

        final AuthCredentials login = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        assertThat(login).isNotNull();
    }

}