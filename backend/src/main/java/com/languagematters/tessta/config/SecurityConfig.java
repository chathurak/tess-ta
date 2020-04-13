package com.languagematters.tessta.config;

import java.util.ArrayList;
import java.util.List;

import com.languagematters.tessta.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler, HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    // @Bean
	// public ClientRegistrationRepository clientRegistrationRepository() {
	// 	List<ClientRegistration> registrations = new ArrayList<>();
	// 	registrations.add(googleClientRegistration());
	// 	return new InMemoryClientRegistrationRepository(registrations);
	// }

	// private ClientRegistration googleClientRegistration() {
	// 	return ClientRegistration.withRegistrationId("google")
    //             .clientId("google-client-id")
	// 			.clientSecret("google-client-secret")
    //             .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
	// 			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	// 			.redirectUriTemplate("{baseUrl}/oauth2/callback/{registrationId}")
	// 			.scope("openid", "profile", "email", "address", "phone")
	// 			.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
	// 			.tokenUri("https://www.googleapis.com/oauth2/v4/token")
	// 			.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
	// 			.userNameAttributeName(IdTokenClaimNames.SUB)
    //             .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
	// 			.clientName("Google").build();
	// }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .formLogin()
                    .disable()
                .httpBasic()
                .   disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers("/index*",
                            "/static/**",
                            "/*.js",
                            "/*.json",
                            "/*.ico")
                        .permitAll()
                    .antMatchers(
                            "/oauth2/**",
                            "/test")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                .oauth2Login()
                    .loginPage("/signin")
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);
    }

}
