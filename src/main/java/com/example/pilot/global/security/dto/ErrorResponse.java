package com.example.pilot.global.security.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String code,
        String message,
        int status
) {

}