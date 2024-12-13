package boot.tokentest.auth.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
public class JjwtTokenProvider implements TokenProvider {

    private final KeyProvider keyProvider;

    public JjwtTokenProvider(final KeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    @Override
    public String extractMemberEmail(final String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public String extractJti(final String token) {
        return extractClaims(token).getId();
    }

    @Override
    public Date extractExpiration(final String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(final String token) {
        return Jwts.parser().verifyWith(keyProvider.getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public String createToken(final String id) {
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis()))
                .id(UUID.randomUUID().toString())
                .signWith(keyProvider.getSecretKey())
                .compact();
    }
}
