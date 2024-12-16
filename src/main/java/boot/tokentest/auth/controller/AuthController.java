package boot.tokentest.auth.controller;

import boot.tokentest.auth.domain.AuthCredential;
import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.service.AuthService;
import boot.tokentest.member.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/auth/test")
    public Map<String, AuthCredential> getAuthService() {
        return authService.getCredentials();
    }
}
