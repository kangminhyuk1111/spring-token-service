package boot.tokentest.auth.service;

import boot.tokentest.auth.domain.AuthCredentials;
import boot.tokentest.auth.provider.TokenProvider;
import boot.tokentest.auth.repository.JwtRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtParser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtService {

    private final TokenProvider tokenProvider;
    private final JwtRepository jwtRepository;
    private final ObjectMapper objectMapper;

    public JwtService(final TokenProvider tokenProvider, final JwtRepository jwtRepository, final ObjectMapper objectMapper) {
        this.tokenProvider = tokenProvider;
        this.jwtRepository = jwtRepository;
        this.objectMapper = objectMapper;
    }

    public String getEmail(final String token) {
        return tokenProvider.extractMemberEmail(token);
    }

    public String getJti(final String token) {
        return tokenProvider.extractJti(token);
    }

    public AuthCredentials generateAuthCredentials(final String email, final String password) {
        final String jti = UUID.randomUUID().toString();

        final String accessToken = tokenProvider.createToken(email);
        final String refreshToken = tokenProvider.createToken(email);

        return new AuthCredentials(jti, accessToken, refreshToken);
    }

    public String getTokenInfo(final String email) {
        final String token = tokenProvider.createToken(email);
        System.out.println(tokenProvider.extractJti(token));
        System.out.println(tokenProvider.extractExpiration(token));
        System.out.println(tokenProvider.extractMemberEmail(token));
        return token;
    }
}
