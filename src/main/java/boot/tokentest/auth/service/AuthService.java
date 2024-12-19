package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.dto.LogoutRequestDto;
import boot.tokentest.auth.dto.RefreshTokenDto;
import boot.tokentest.auth.repository.JwtRepository;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import boot.tokentest.member.domain.Member;
import boot.tokentest.member.service.MemberService;
import boot.tokentest.member.service.PasswordService;
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
        final Member member = memberService.findByEmail(loginRequestDto.getEmail());
        final boolean matches = passwordService.matches(loginRequestDto.getPassword(), member.password().getPassword());

        if (!matches) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_MATCHES);
        }

        return jwtService.generateAuthCredential(member.email());
    }

    public AuthCredential refreshToken(final RefreshTokenDto refreshTokenDto) {
        return jwtService.refreshToken(refreshTokenDto.refreshToken());
    }

    public void logout(final LogoutRequestDto logoutRequestDto) {
        jwtRepository.deleteByJti(logoutRequestDto.getJti());
    }
}
