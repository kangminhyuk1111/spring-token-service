package boot.tokentest.auth.service;

import boot.tokentest.auth.provider.KeyProvider;
import boot.tokentest.auth.provider.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final KeyProvider keyProvider;
    private final TokenProvider tokenProvider;

    public JwtService(final KeyProvider keyProvider, final TokenProvider tokenProvider) {
        this.keyProvider = keyProvider;
        this.tokenProvider = tokenProvider;
    }

    public String createToken(final String email) {
        return tokenProvider.createToken(email, keyProvider.getSecretKey());
    }
}
