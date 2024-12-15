package boot.tokentest.auth.repository;

import boot.tokentest.auth.domain.AuthCredentials;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JwtRepository {

    private final Map<String, AuthCredentials> credentials = new HashMap<>();
}
