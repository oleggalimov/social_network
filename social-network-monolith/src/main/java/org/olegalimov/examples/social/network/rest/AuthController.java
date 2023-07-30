package org.olegalimov.examples.social.network.rest;

import lombok.AllArgsConstructor;
import org.olegalimov.examples.social.network.dto.LoginRequestDto;
import org.olegalimov.examples.social.network.dto.LoginResponseDto;
import org.olegalimov.examples.social.network.utils.JwtUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public LoginResponseDto authenticateUser(@RequestBody LoginRequestDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getId(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtUtils.generateJwtToken(authentication);

        return new LoginResponseDto(jwt);
    }
}
