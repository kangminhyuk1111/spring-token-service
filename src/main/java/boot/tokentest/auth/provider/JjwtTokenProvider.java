package boot.tokentest.auth.provider;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
public class JjwtTokenProvider implements TokenProvider {


    @Override
    public String extractMemberEmail(final String token) {
        return "";
    }

    @Override
    public String extractJti(final String token) {
        return "";
    }

    @Override
    public String extractExpiration(final String token) {
        return "";
    }

    @Override
    public String createToken(final String id, final SecretKey secretKey) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofHours(1).toMillis()))
                .setId(UUID.randomUUID().toString())
                .signWith(secretKey)
                .compact();
    }
}
