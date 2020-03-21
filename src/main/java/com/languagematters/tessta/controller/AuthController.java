package com.languagematters.tessta.controller;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        signInRequest.getUsernameOrEmail(),
//                        signInRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.generateToken(authentication);
        String jwt = "xxxx";
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

}

@Getter
@Setter
class SignInRequest {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

}

@Getter
@Setter
@RequiredArgsConstructor
class JwtAuthenticationResponse {

    private String tokenType = "Bearer";

    @NonNull
    private String accessToken;

}