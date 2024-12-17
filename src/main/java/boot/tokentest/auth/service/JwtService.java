package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.provider.JtiProvider;
import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final TokenProvider tokenProvider;
    private final JwtRepository jwtRepository;
    private final JtiProvider jtiProvider;

    public JwtService(final TokenProvider tokenProvider, final JwtRepository jwtRepository, final JtiProvider jtiProvider) {
        this.tokenProvider = tokenProvider;
        this.jwtRepository = jwtRepository;
        this.jtiProvider = jtiProvider;
    }

    public AuthCredential generateAuthCredential(final String email) {
        final String jti = jtiProvider.generateJti();

        final String accessToken = tokenProvider.createToken(email, jti);
        final String refreshToken = tokenProvider.createToken(email, jti);

        final AuthCredential authCredential = new AuthCredential(jti, accessToken, refreshToken);

        jwtRepository.saveCredential(authCredential);

        return authCredential;
    }

    public Claims extractClaimsFromToken(final String accessToken) {
        return tokenProvider.extractClaimsFromToken(accessToken);
    }
}
