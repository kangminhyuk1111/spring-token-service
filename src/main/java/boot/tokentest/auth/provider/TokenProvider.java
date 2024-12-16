package boot.tokentest.auth.provider;

public interface TokenProvider {

    String createToken(String email);
}
