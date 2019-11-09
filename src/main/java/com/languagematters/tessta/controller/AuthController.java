package com.languagematters.tessta.controller;

import com.languagematters.tessta.exception.AppException;
import com.languagematters.tessta.jpa.entity.Role;
import com.languagematters.tessta.jpa.entity.RoleName;
import com.languagematters.tessta.jpa.entity.User;
import com.languagematters.tessta.jpa.repository.RoleRepository;
import com.languagematters.tessta.jpa.repository.UserRepository;
import com.languagematters.tessta.security.CurrentUser;
import com.languagematters.tessta.security.JwtTokenProvider;
import com.languagematters.tessta.security.UserPrincipal;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsernameOrEmail(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new SignUpResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new SignUpResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new SignUpResponse(true, "User registered successfully"));
    }

    @PostMapping("/token")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateToken(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UpdateTokenRequest updateTokenRequest) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + currentUser.getId())
        );

        user.setAccessToken(updateTokenRequest.getAccessToken());
        user.setImageUrl(updateTokenRequest.getImageUrl());

        userRepository.save(user);

        return ResponseEntity.ok(String.format("User %s updated successfully", currentUser.getUsername()));
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
class SignUpRequest {

    @NotBlank
    @Size(max = 40)
    private String firstName;

    @NotBlank
    @Size(max = 40)
    private String lastName;

    @NotBlank
    @Size(min = 5, max = 40)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

}

@Getter
@Setter
@RequiredArgsConstructor
class SignUpResponse {

    @NonNull
    private Boolean success;

    @NonNull
    private String message;

}

@Getter
@Setter
class UpdateTokenRequest {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String imageUrl;

}

@Getter
@Setter
@RequiredArgsConstructor
class JwtAuthenticationResponse {

    private String tokenType = "Bearer";

    @NonNull
    private String accessToken;

}