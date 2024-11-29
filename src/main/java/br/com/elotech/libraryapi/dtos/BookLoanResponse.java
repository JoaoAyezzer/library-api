package br.com.elotech.libraryapi.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookLoanResponse(
        UUID id,
        UUID bookId,
        String bookTitle,
        UUID userId,
        String userName,
        LocalDateTime loanDate,
        LocalDateTime returnDate,
        LocalDateTime updatedAt,
        String status
) {
}
