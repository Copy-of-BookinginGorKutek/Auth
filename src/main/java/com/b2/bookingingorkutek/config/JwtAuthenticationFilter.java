package com.b2.bookingingorkutek.config;

import com.b2.bookingingorkutek.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Generated;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Generated
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    private static final String JWT_HEADER = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(JWT_HEADER);
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith(JWT_TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtToken = authHeader.substring(7);
            System.out.println(jwtToken);
            userEmail = jwtService.extractUsername(jwtToken);
            System.out.println(userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch(Exception e){
            logger.warn("Authentication Failed");
        }
        filterChain.doFilter(request, response);
    }
}