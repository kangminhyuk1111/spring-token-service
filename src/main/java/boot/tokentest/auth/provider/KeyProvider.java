package boot.tokentest.auth.provider;

import javax.crypto.SecretKey;

public interface KeyProvider {

    SecretKey getSecretKey();
}
