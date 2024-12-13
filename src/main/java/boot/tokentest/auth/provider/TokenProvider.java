package boot.tokentest.auth.provider;

import javax.crypto.SecretKey;

public interface TokenProvider {

    String extractMemberEmail(final String token);

    String extractJti(final String token);

    String extractExpiration(final String token);

    String createToken(String id, SecretKey secretKey);
}
