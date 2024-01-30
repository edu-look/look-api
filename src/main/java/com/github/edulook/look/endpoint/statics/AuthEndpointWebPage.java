package com.github.edulook.look.endpoint.statics;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping
@AllArgsConstructor
public class AuthEndpointWebPage {
    @GetMapping("login")
    public String login() {
        return "login.html";
    }
}
