package boot.tokentest.auth.dto;

public class LoginRequestDto {

    private String email;
    private String password;

    public LoginRequestDto() {
    }

    public LoginRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
