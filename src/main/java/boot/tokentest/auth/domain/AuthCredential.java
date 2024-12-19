package boot.tokentest.auth.domain;

import java.util.Objects;

public record AuthCredential(String jti, String accessToken, String refreshToken) {

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthCredential that = (AuthCredential) o;
        return Objects.equals(jti, that.jti) && Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jti, accessToken, refreshToken);
    }
}
