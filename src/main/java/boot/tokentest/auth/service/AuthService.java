package boot.tokentest.auth.service;

import boot.tokentest.auth.dto.LoginRequestDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;

    public AuthService(final JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String login(final LoginRequestDto tokenRequest) {
        final String email = tokenRequest.getEmail();
        return jwtService.createToken(email);
    }
}
