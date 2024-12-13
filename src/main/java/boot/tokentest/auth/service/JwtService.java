package boot.tokentest.auth.service;

import boot.tokentest.auth.provider.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final TokenProvider tokenProvider;

    public JwtService(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public String createToken(final String email) {
        return tokenProvider.createToken(email);
    }
}
