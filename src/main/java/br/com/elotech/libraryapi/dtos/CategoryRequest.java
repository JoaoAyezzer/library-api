package br.com.elotech.libraryapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank String name) {
}
