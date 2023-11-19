package com.github.edulook.look.endpoint.io.shared;

import java.time.Instant;

import lombok.Builder;

@Builder
public record UserAuthDTO(
    String name,
    String id,
    String email,
    String photo, 
    JWT jwt
) {
    @Builder
    public static record JWT(
        String token,
        Instant expiresAt,
        Instant createdAt
    ){}
}
