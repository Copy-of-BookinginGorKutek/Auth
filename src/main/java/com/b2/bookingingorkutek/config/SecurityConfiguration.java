package com.b2.bookingingorkutek.config;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Generated
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**", "/auth-page/**", "/reservation-page/**", "/api/v1/auth/authorization/**",
                        "/user-reservation-page/**", "/api/v1/frontend/create-reservation/**", "/home/**", "/api/v1/frontend/pay/**",
                        "/api/v1/frontend/user-notification/**", "/admin-coupon-page/**", "/create-coupon/**",
                        "/api/v1/frontend/create-lapangan/**", "/lapangan-page/**", "/admin-page/**", "/api/v1/frontend/status/**",
                        "/api/v1/frontend/operasional-lapangan/**", "/operasional-lapangan-page/**", "/api/v1/frontend/coupon/**", "/v3/**", "/**")
                .permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutSuccessUrl("/auth-page/login")
                .deleteCookies("token");

        return http.build();
    }
}
