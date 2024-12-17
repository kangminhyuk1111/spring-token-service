package boot.tokentest.auth.controller;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.dto.AuthDto;
import boot.tokentest.auth.dto.GetCredentialRequestDto;
import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.dto.LogoutRequestDto;
import boot.tokentest.auth.filter.JwtAuthenticationFilter;
import boot.tokentest.auth.service.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public AuthCredential login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/auth/logout")
    public void logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        authService.logout(logoutRequestDto);
    }

    @GetMapping("/auth/accessToken")
    public String authTest(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestHeader(value = "jti", required = false) String jti) {
        return authorizationHeader;
    }
}
