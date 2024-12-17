package boot.tokentest.auth.dto;

public class LogoutRequestDto {

    private String jti;

    public LogoutRequestDto() {
    }

    public LogoutRequestDto(final String jti) {
        this.jti = jti;
    }

    public String getJti() {
        return jti;
    }
}
