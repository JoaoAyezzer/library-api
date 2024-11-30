package br.com.elotech.libraryapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record BookRequest(
        @NotBlank String title,
        @NotNull Set<@NotBlank String> isbns,
        @NotNull Set<@NotBlank String> authors,
        @NotNull LocalDate publishedDate,
        @NotNull UUID categoryId) {
}
