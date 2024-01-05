package com.example.pilot.global.security.config;

import com.example.pilot.global.security.AccessTokenProvider;
import com.example.pilot.global.security.RefreshTokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    @Bean(name = "accessTokenProvider")
    public AccessTokenProvider accessTokenProvider(JwtProperties jwtProperties) {
        return new AccessTokenProvider(jwtProperties.getSecret(), jwtProperties.getAccessTokenValidityInSeconds());
    }

    @Bean(name = "refreshTokenProvider")
    public RefreshTokenProvider refreshTokenProvider(JwtProperties jwtProperties) {
        return new RefreshTokenProvider(jwtProperties.getRefreshTokenSecret(), jwtProperties.getRefreshTokenValidityInSeconds());
    }
}