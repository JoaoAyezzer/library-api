package br.com.elotech.libraryapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record BookRequest(
        @NotBlank String title,
        @NotBlank String isbn,
        @NotBlank String author,
        @NotNull LocalDate publishedDate,
        @NotNull UUID categoryId) {
}
