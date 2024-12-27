package boot.tokentest.member.vo;

import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

public class Password {

    private static final Pattern PASSOWRD_PATTERN =
            Pattern.compile("^(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]{4,16}$");

    private String password;

    public Password(final PasswordEncoder passwordEncoder, final String password) {
        validatePassword(password);
        this.password = passwordEncoder.encode(password);
    }

    private void validatePassword(final String password) {
        if (password == null || password.isBlank()) {
            throw new ApplicationException(ErrorCode.PASSWORD_VALID_EXCEPTION);
        }

        if (!PASSOWRD_PATTERN.matcher(password).matches()) {
            throw new ApplicationException(ErrorCode.PASSWORD_VALID_EXCEPTION);
        }
    }

    public String getPassword() {
        return password;
    }
}
