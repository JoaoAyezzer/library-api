package br.com.elotech.libraryapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String phone
) {
}
