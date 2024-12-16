package boot.tokentest.member.dto;

import boot.tokentest.member.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberSignupRequestDto {

    private String username;
    private String email;
    private String password;

    public MemberSignupRequestDto(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public MemberSignupRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return new Member(username, email, passwordEncoder.encode(password));
    }
}
