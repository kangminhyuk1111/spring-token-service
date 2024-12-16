package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtService {

    private final TokenProvider tokenProvider;
    private final JwtRepository jwtRepository;

    public JwtService(final TokenProvider tokenProvider, final JwtRepository jwtRepository) {
        this.tokenProvider = tokenProvider;
        this.jwtRepository = jwtRepository;
    }

    public AuthCredential generateAuthCredential(final String email) {
        final String jti = UUID.randomUUID().toString();

        final String accessToken = tokenProvider.createToken(email);
        final String refreshToken = tokenProvider.createToken(email);

        final AuthCredential authCredential = new AuthCredential(jti, accessToken, refreshToken);

        jwtRepository.saveCredential(authCredential);

        return authCredential;
    }
}
