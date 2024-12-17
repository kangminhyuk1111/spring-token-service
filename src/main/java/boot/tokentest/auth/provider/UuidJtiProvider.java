package boot.tokentest.auth.provider;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidJtiProvider implements JtiProvider{

    @Override
    public String generateJti() {
        return UUID.randomUUID().toString();
    }
}
