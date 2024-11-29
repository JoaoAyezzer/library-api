package br.com.elotech.libraryapi.dtos;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String phone,
        String createdAt,
        String updatedAt
) {
}
