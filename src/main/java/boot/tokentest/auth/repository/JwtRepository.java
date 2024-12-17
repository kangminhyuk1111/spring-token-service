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

    public void deleteByJti(final String jti) {
        credentials.remove(jti);
    }

    public boolean isAccessTokenPresent(final String accessToken) {
        System.out.println("accessToken: " + accessToken);
        return credentials.values().stream()
                .anyMatch(credential -> credential.accessToken().equals(accessToken));
    }
}
