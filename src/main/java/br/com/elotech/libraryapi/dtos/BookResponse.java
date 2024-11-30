package br.com.elotech.libraryapi.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        Set<String> isbns,
        Set<String> authors,
        LocalDate publishedDate,
        UUID categoryId,
        String categoryName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {
}
