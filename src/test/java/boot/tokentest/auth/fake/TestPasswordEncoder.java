package boot.tokentest.auth.fake;

import org.springframework.security.crypto.password.PasswordEncoder;

public class TestPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final CharSequence rawPassword) {
        return "";
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return false;
    }
}
