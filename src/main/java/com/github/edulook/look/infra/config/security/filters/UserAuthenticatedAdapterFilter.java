package com.github.edulook.look.infra.config.security.filters;

import com.github.edulook.look.endpoint.internal.mapper.shared.OAuth2AndUserAuthDTOMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

import static com.github.edulook.look.utils.LookUtils.toUserDTO;

@Configuration
@Slf4j
public class UserAuthenticatedAdapterFilter implements Filter {

    private final OAuth2AndUserAuthDTOMapper mapper;

    public UserAuthenticatedAdapterFilter(OAuth2AndUserAuthDTOMapper mapper) {
        this.mapper = mapper;
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .ifPresent(auth -> {
                    var userDTO = toUserDTO(auth.getPrincipal());
                    userDTO.ifPresent(user -> {
                        request.setAttribute("user", user);
                        var httpServletResponse = ((HttpServletResponse) response);
                        httpServletResponse.addHeader("Authorization", String.format("Bearer %s", user.jwt().token()));
                    });
                });
        }
        catch (Exception e) {
            log.error("error:: ", e);
            throw e;
        }
        chain.doFilter(request, response);
    }
}
