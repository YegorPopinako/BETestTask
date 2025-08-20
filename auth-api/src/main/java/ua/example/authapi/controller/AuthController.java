package ua.example.authapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.authapi.dto.UserDto;
import ua.example.authapi.model.User;
import ua.example.authapi.service.AuthService;
import ua.example.authapi.service.TokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDto userDto) {
        authService.registerUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        User user = authService.findByEmail(userDto.getEmail()).get();

        authService.checkCredentials(user, userDto);
        String token = tokenService.generateToken(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }
}
