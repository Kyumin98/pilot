package com.example.pilot.domain.login.account.dto;

import com.example.pilot.domain.login.account.domain.Account;
import com.example.pilot.domain.login.account.domain.Authority;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

public record ResponseAccount()
{
    @Builder
    public record Token(
            String accessToken,
            String refreshToken
    ) {
    }

    @Builder
    public record Information(
            String username,
            String password,
            String nickname,
            Long tokenWeight,
            Set<String> authoritySet
    ) {

        public static ResponseAccount.Information of(Account account) {
            if(account == null) return null;

            return ResponseAccount.Information.builder()
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .nickname(account.getNickname())
                    .tokenWeight(account.getTokenWeight())
                    .authoritySet(account.getAuthorities().stream()
                            .map(Authority::getAuthorityName)
                            .collect(Collectors.toSet()))
                    .build();
        }
    }
}