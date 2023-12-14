package com.github.edulook.look.infra.config.security.filters;

import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import jakarta.servlet.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.io.IOException;

@Configuration
public class UserAuthenticatedAdapterFilter implements Filter {

    private final OAuth2AndUserAuthDTOMapper mapper;

    public UserAuthenticatedAdapterFilter(OAuth2AndUserAuthDTOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var details = (WebAuthenticationDetails) auth.getDetails();

        if(details.getSessionId() == null) {
            request.setAttribute("user", mapper.toDTO((Jwt) auth.getPrincipal()));
        }
        else {
            request.setAttribute("user", mapper.toDTO((OAuth2User) auth.getPrincipal()));
        }

        chain.doFilter(request, response);
    }

}
