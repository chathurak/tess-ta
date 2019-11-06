package com.languagematters.tessta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = {
            "/",
            "/signin",
            "/signup",
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
