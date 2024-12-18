package boot.tokentest.member.domain;

import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import boot.tokentest.member.vo.Password;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void 멤버_생성_성공() {
        // given & when
        final Member member = new Member("validUser", "test@example.com", new Password(passwordEncoder, "testpassword1"));

        // then
        assertThat(member).isNotNull();
    }

    @Test
    void username이_4자이하로_들어온_경우_ApplicationException_발생() {
        // given & when & then
        assertThatThrownBy(() -> new Member("abc", "test@example.com", new Password(passwordEncoder, "testpassword1")))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ErrorCode.USERNAME_VALID_EXCEPTION.getMessage());
    }

    @Test
    void username이_16자이상으로_들어온_경우_ApplicationException_발생() {
        // given & when & then
        assertThatThrownBy(() -> new Member("1q2w3e4r5t6y7u8i9o0p", "test@example.com", new Password(passwordEncoder, "testpassword1")))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ErrorCode.USERNAME_VALID_EXCEPTION.getMessage());
    }

    @Test
    void email이_약속된_방식으로_들어온_경우_ApplicationException_발생() {
        // given & when & then
        assertThatThrownBy(() -> new Member("test1234", "test", new Password(passwordEncoder, "testpassword1")))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ErrorCode.EMAIL_VALID_EXCEPTION.getMessage());
    }

    @Test
    void 패스워드가_정규식을_통과하지_못하는경우_ApplicationException_발생() {
        // given & when & then
        assertThatThrownBy(() -> new Member("validUser", "test@example.com", new Password(passwordEncoder, "ttt")))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ErrorCode.PASSWORD_VALID_EXCEPTION.getMessage());
    }
}