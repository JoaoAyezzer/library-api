package br.com.elotech.libraryapi.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record BookLoanRequest(
    @NotNull UUID bookId,
    @NotNull UUID userId,
    @NotNull LocalDate returnDate
) {
}
