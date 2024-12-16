package boot.tokentest.auth.repository;

import boot.tokentest.auth.domain.AuthCredential;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JwtRepository {

    private final Map<String, AuthCredential> credentials = new HashMap<>();

    public void saveCredential(final AuthCredential credential) {
        credentials.put(credential.jti(), credential);
    }

    public AuthCredential getCredential(final String jti) {
        return credentials.get(jti);
    }

    public Map<String, AuthCredential> getCredentials() {
        return credentials;
    }
}
