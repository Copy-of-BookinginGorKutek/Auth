package com.b2.bookingingorkutek.config;

import com.b2.bookingingorkutek.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private static final String JWT_HEADER = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer";
    private static final String COOKIE_NAME = "token";
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(JWT_HEADER);
        String jwtToken;
        final String userEmail;

        jwtToken = getJwtTokenFromCookie(request);
        if (jwtToken == null && (authHeader == null || !authHeader.startsWith(JWT_TOKEN_PREFIX))) {
            filterChain.doFilter(request, response);
            return;
        }
        if(jwtToken == null)
            jwtToken = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(jwtToken);
            updateSecurityContextHolder(request, response, jwtToken, userEmail);
        }catch(Exception ignored){}
        filterChain.doFilter(request, response);
    }

    private void updateSecurityContextHolder(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, String jwtToken, String userEmail) throws IOException, ServletException {
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (UsernameNotFoundException ignored) {
            }
        }
    }
    private String getJwtTokenFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            return null;
        for(Cookie cookie:cookies)
            if(cookie.getName().equals(COOKIE_NAME)) {
                return cookie.getValue();
            }
        return null;
    }
}
