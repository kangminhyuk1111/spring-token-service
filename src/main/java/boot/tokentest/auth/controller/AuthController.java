package boot.tokentest.auth.controller;

import boot.tokentest.auth.domain.AuthCredentials;
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
    public AuthCredentials createToken(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/auth/test")
    public String getTokenInfo(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.getTokenInfo(loginRequestDto);
    }
}
