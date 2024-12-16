package boot.tokentest.auth.provider;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JjwtTokenProvider implements TokenProvider{

    private final KeyProvider keyProvider;

    public JjwtTokenProvider(final KeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    @Override
    public String createToken(final String email) {
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis()))
                .issuedAt(new Date())
                .signWith(keyProvider.getSecretKey())
                .compact();
    }
}
