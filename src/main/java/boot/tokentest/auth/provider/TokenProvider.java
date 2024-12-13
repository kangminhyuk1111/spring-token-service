package boot.tokentest.auth.provider;

import java.util.Date;

public interface TokenProvider {

    String extractMemberEmail(final String token);

    String extractJti(final String token);

    Date extractExpiration(final String token);

    String createToken(String id);
}
