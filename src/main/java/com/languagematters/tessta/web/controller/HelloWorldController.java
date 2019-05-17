package com.languagematters.tessta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @RequestMapping("/helloworld")
    public @ResponseBody String greeting() {
        return "Hello World";
    }

}