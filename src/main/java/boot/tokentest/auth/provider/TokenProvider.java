package boot.tokentest.auth.provider;

import io.jsonwebtoken.Claims;

import java.util.Date;

public interface TokenProvider {

    String createToken(String email, String jti);

    Claims extractClaimsFromToken(String accessToken);

    String extractEmailFromToken(String accessToken);

    Date extractExpirationTimeFromToken(String accessToken);

    String extractJtiFromToken(String accessToken);
}
