package boot.tokentest.auth.domain;

import java.util.Objects;

public class AuthCredentials {

    private final String jti;
    private final String accessToken;
    private final String refreshToken;

    public AuthCredentials(final String jti, final String accessToken, final String refreshToken) {
        this.jti = jti;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public boolean isRefreshTokenValid(final String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }

    public String getJti() {
        return jti;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final AuthCredentials that)) return false;
        return Objects.equals(jti, that.jti) && Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jti, accessToken, refreshToken);
    }
}
