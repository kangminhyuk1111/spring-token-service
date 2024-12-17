package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.dto.GetCredentialRequestDto;
import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.dto.LogoutRequestDto;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import boot.tokentest.member.domain.Member;
import boot.tokentest.member.service.MemberService;
import boot.tokentest.member.service.PasswordService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

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
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCHES);
        }

        return jwtService.generateAuthCredential(email);
    }

    public void logout(final LogoutRequestDto logoutRequestDto) {
        jwtRepository.deleteByJti(logoutRequestDto.getJti());
    }

    public AuthCredential findByJti(final GetCredentialRequestDto getCredentialRequestDto) {
        return jwtRepository.findByJti(getCredentialRequestDto.getJti());
    }

    public Claims extractJwt(final String accessToken) {
        return jwtService.extractClaimsFromToken(accessToken);
    }
}
