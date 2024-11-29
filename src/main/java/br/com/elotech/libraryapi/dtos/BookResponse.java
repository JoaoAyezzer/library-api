package br.com.elotech.libraryapi.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        String isbn,
        String author,
        String publishedDate,
        UUID categoryId,
        String categoryName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {
}
