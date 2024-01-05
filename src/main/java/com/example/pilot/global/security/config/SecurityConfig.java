package com.example.pilot.global.security.config;

import com.example.pilot.global.security.exception.JwtAccessDeniedHandler;
import com.example.pilot.global.security.exception.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, CustomJwtFilter customJwtFilter) throws Exception {
        httpSecurity
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/h2/**").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/error").permitAll()

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/actuator/*").permitAll()

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/hello").permitAll()
                .requestMatchers("/api/v1/accounts/token").permitAll()
                .requestMatchers("/api/v1/user/register").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}