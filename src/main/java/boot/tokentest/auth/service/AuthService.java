package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredentials;
import boot.tokentest.auth.dto.LoginRequestDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;

    public AuthService(final JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public AuthCredentials login(final String email, final String password) {
        return jwtService.generateAuthCredentials(email, password);
    }

    public String getTokenInfo(final LoginRequestDto loginRequestDto) {
        return jwtService.getTokenInfo(loginRequestDto.getEmail());
    }
}
