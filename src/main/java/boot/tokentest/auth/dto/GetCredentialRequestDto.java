package boot.tokentest.auth.dto;

public class GetCredentialRequestDto {

    private String jti;

    public GetCredentialRequestDto(final String jti) {
        this.jti = jti;
    }

    public String getJti() {
        return jti;
    }
}
