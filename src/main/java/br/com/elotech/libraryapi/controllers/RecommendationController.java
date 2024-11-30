package br.com.elotech.libraryapi.controllers;

import br.com.elotech.libraryapi.dtos.BookResponse;
import br.com.elotech.libraryapi.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<BookResponse>> recommendBooks(@PathVariable UUID userId) {
        var result = recommendationService.recommendBooksForUser(userId);
        return ResponseEntity.ok(result);
    }
}
