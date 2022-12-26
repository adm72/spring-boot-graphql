package ru.jbru.springbootgraphql.service;

import ru.jbru.springbootgraphql.model.dto.SigninRequest;
import ru.jbru.springbootgraphql.model.dto.SignupRequest;
import ru.jbru.springbootgraphql.model.dto.TokenResponse;

public interface AuthService {

    TokenResponse signup(SignupRequest signupRequest);

    TokenResponse signin(SigninRequest signinRequest);

}
