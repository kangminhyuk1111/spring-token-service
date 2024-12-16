package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.provider.KeyProvider;
import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.member.domain.Member;
import boot.tokentest.member.service.MemberService;
import boot.tokentest.member.service.PasswordService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final JwtRepository jwtRepository;
    private final PasswordService passwordService;
    private final MemberService memberService;
    private final JwtService jwtService;

    public AuthService(final JwtRepository jwtRepository, final PasswordService passwordService, final MemberService memberService, final JwtService jwtService) {
        this.jwtRepository = jwtRepository;
        this.passwordService = passwordService;
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    public AuthCredential login(final LoginRequestDto loginRequestDto) {
        final String email = loginRequestDto.getEmail();
        final String password = loginRequestDto.getPassword();

        final Member member = memberService.findByEmail(email);

        final boolean matches = passwordService.matches(password, member.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateAuthCredential(email);
    }

    public Map<String, AuthCredential> getCredentials() {
        return jwtRepository.getCredentials();
    }
}
