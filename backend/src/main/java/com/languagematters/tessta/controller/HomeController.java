package com.languagematters.tessta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping(value = {
            "/login",
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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Principal testFun() {
        return null;
    }

}
