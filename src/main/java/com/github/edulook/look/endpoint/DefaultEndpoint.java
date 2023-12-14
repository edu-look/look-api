package com.github.edulook.look.endpoint;

import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("v1")
public class DefaultEndpoint {

    @GetMapping
    public void rootRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/user");
    }

    @GetMapping("/user")
    public Object user(@RequestAttribute("user") UserAuthDTO user) {
        return  user;
    }
}
