package boot.tokentest.auth.dto;

public class AuthDto {

    private String accessToken;

    public AuthDto(final String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthDto() {
    }

    public String getAccessToken() {
        return accessToken;
    }
}
