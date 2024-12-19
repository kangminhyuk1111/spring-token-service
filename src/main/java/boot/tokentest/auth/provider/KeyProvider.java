package boot.tokentest.auth.provider;

import javax.crypto.SecretKey;
import java.util.Date;

public interface KeyProvider {

    SecretKey getSecretKey();

    Date getExpiration();
}
