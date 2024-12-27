package boot.tokentest.member.vo;

import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.member.config.PasswordConfig;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordTest {

    private final PasswordConfig passwordConfig = new PasswordConfig();

    public PasswordEncoder getPasswordEncoder() {
        return passwordConfig.passwordEncoder();
    }

    @Test
    void 비밀번호_생성_테스트() {
        String testPw1 = "abcD1@"; // 유효
        String testPw2 = "abcd1234"; // 유효하지 않음 (특수 문자 없음)
        String testPw3 = "Abcd@"; // 유효
        String testPw4 = "aB1f!"; // 유효
        String testPw5 = "abcd"; // 유효하지 않음 (특수 문자 없음)

        assertAll(
                () -> assertThat(new Password(getPasswordEncoder(), testPw1)).isInstanceOf(Password.class),
                () -> assertThat(new Password(getPasswordEncoder(), testPw3)).isInstanceOf(Password.class),
                () -> assertThat(new Password(getPasswordEncoder(), testPw4)).isInstanceOf(Password.class),
                () -> assertThrows(ApplicationException.class, () -> new Password(getPasswordEncoder(), testPw2)),
                () -> assertThrows(ApplicationException.class, () -> new Password(getPasswordEncoder(), testPw5))
        );
    }
}
