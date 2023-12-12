package com.github.edulook.look.endpoint;

import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import com.github.edulook.look.endpoint.io.shared.UserAuthDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class DefaultEndpoint {

    private final OAuth2AndUserAuthDTOMapper oAuthMapper;

    @GetMapping
    public void rootRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/user");
    }

    @GetMapping("/user")
    public UserAuthDTO user(@AuthenticationPrincipal OAuth2User user) {
        return oAuthMapper.toDTO(user);
    }
}
