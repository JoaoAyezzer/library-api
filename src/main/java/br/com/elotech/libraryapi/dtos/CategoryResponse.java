package br.com.elotech.libraryapi.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
