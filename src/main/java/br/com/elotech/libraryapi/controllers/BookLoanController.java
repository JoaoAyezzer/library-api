package br.com.elotech.libraryapi.controllers;

import br.com.elotech.libraryapi.dtos.BookLoanRequest;
import br.com.elotech.libraryapi.dtos.BookLoanResponse;
import br.com.elotech.libraryapi.services.BookLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book-loans")
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid BookLoanRequest request){
        var result = service.create(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookLoanResponse> findById(@PathVariable UUID id){
        var result = service.findDtoById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BookLoanResponse>> findByUser(@PathVariable UUID id){
        var result = service.findAllByUserId(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<BookLoanResponse>> findAll(){
        var result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id){
        service.update(id);
        return ResponseEntity.noContent().build();
    }
}
