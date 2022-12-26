package ru.jbru.springbootgraphql.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jbru.springbootgraphql.model.dto.SigninRequest;
import ru.jbru.springbootgraphql.model.dto.SignupRequest;
import ru.jbru.springbootgraphql.model.dto.TokenResponse;
import ru.jbru.springbootgraphql.service.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/auth")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> registrationUser(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> authenticationUser(@RequestBody SigninRequest signupRequest) {
        return ResponseEntity.ok(authService.signin(signupRequest));
    }
}
