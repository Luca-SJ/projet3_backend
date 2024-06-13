package com.projet3.demo;

import com.projet3.demo.Services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JWTFilter extends GenericFilterBean {

        private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);

        private final JWTService tokenProvider;

        public JWTFilter(JWTService tokenProvider) {

            this.tokenProvider = tokenProvider;
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
                ServletException {

                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                String jwt = this.resolveToken(httpServletRequest);
                if (StringUtils.hasText(jwt)) {
                    if (this.tokenProvider.validateToken(jwt)) {
                        Authentication authentication = this.tokenProvider.getContext(jwt);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                filterChain.doFilter(servletRequest, servletResponse);
        }

        private String resolveToken(HttpServletRequest request) {

            String bearerToken = request.getHeader("AUTHORIZATION");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                String jwt = bearerToken.substring(7, bearerToken.length());
                return jwt;
            }
            return null;
        }
}
