package com.github.edulook.look.endpoint;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class DefaultEndpoint {

    @GetMapping
    void rootRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/v1/students/profile");
    }
}
