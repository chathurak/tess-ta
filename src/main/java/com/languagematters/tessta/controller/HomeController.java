package com.languagematters.tessta.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    private final ClientRegistrationRepository clientRegistrationRepository;

    @RequestMapping(value = {
            "/signin",
            "/",
            "/home",
            "/library",
            "/queue",
            "/ocr",
            "/grammar",
            "/report",
            "/settings"
    })
    public String index() {
        return "index.html";
    }

}
