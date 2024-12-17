package boot.tokentest.auth.repository;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.global.exception.ApplicationException;
import boot.tokentest.global.exception.ErrorCode;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JwtRepository {

    private final Map<String, AuthCredential> credentials = new HashMap<>();

    public void saveCredential(final AuthCredential credential) {
        credentials.put(credential.jti(), credential);
    }

    public AuthCredential findByJti(final String jti) {
        final AuthCredential authCredential = credentials.get(jti);

        if (authCredential == null) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED);
        }

        return authCredential;
    }

    public boolean isPresentJti(final String jti) {
        return credentials.containsKey(jti);
    }

    public void deleteByJti(final String jti) {
        credentials.remove(jti);
    }

    public boolean isAccessTokenPresent(final String jti, final String accessToken) {
        final AuthCredential authCredential = credentials.get(jti);

        return authCredential.accessToken().equals(accessToken);
    }
}
