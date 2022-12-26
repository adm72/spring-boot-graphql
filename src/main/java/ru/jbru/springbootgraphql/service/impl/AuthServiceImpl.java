package ru.jbru.springbootgraphql.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jbru.springbootgraphql.model.UserEntity;
import ru.jbru.springbootgraphql.model.dto.SigninRequest;
import ru.jbru.springbootgraphql.model.dto.SignupRequest;
import ru.jbru.springbootgraphql.model.dto.TokenResponse;
import ru.jbru.springbootgraphql.repository.UserRepository;
import ru.jbru.springbootgraphql.security.JWTTokenProvider;
import ru.jbru.springbootgraphql.security.SecurityConstants;
import ru.jbru.springbootgraphql.security.UserRole;
import ru.jbru.springbootgraphql.service.AuthService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse signup(SignupRequest request) {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.USER);

        UserEntity user = UserEntity.builder()
                .roles(userRoles)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .build();

        userRepository.save(user);
        return getTokenResponse(request.getEmail(), request.getPassword());
    }

    @Override
    public TokenResponse signin(SigninRequest request) {
        return getTokenResponse(request.getEmail(), request.getPassword());
    }

    private TokenResponse getTokenResponse(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email.toLowerCase(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        return new TokenResponse(SecurityConstants.TOKEN_PREFIX, accessToken, refreshToken);
    }
}
