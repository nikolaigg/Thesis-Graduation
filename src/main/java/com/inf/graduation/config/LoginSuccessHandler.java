package com.inf.graduation.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "/default";

        if (hasRole(authorities, "ROLE_ADMIN")) {
            redirectUrl = "/admin/students";
        } else if (hasRole(authorities, "ROLE_TEACHER")) {
            redirectUrl = "/teacher/application/my-applications";
        } else if (hasRole(authorities, "ROLE_STUDENT")) {
            redirectUrl = "/student/my-thesis";
        }

        response.sendRedirect(request.getContextPath() + redirectUrl);

    }

    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
