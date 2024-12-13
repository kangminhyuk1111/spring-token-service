package boot.tokentest.auth.controller;

import boot.tokentest.auth.dto.LoginRequestDto;
import boot.tokentest.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public String createToken(@RequestBody LoginRequestDto tokenRequest) {
        return authService.login(tokenRequest);
    }
}
