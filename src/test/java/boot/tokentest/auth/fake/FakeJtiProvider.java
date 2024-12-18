package boot.tokentest.auth.fake;

import boot.tokentest.auth.provider.JtiProvider;

public class FakeJtiProvider implements JtiProvider {

    @Override
    public String generateJti() {
        return "test-jti-123456-123456";
    }
}
