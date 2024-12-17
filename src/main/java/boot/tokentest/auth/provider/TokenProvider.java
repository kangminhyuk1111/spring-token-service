package boot.tokentest.auth.provider;

import io.jsonwebtoken.Claims;

import java.util.Date;

public interface TokenProvider {

    String createToken(String email, String jti);

    Claims extractClaimsFromToken(String accessToken);

    String extractEmailFromClaims(String accessToken);

    Date extractExpirationTimeFromClaims(String accessToken);

    String extractJtiFromClaims(String accessToken);
}
