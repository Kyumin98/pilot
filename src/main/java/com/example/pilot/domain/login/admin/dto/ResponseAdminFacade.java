package com.example.pilot.domain.login.admin.dto;

import lombok.Builder;

import java.util.Set;

public record ResponseAdminFacade() {
    @Builder
    public record Information(
            String username,
            String password,
            String nickname,
            Long tokenWeight,
            Set<String> authoritySet
    ) {
    }
}
