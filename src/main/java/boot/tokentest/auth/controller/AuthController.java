package boot.tokentest.auth.controller;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.dto.LogoutRequestDto;
import boot.tokentest.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthCredential login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        authService.logout(logoutRequestDto);
    }
}
