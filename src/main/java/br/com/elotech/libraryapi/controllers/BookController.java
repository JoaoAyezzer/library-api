package br.com.elotech.libraryapi.controllers;

import br.com.elotech.libraryapi.dtos.BookRequest;
import br.com.elotech.libraryapi.dtos.BookResponse;
import br.com.elotech.libraryapi.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid BookRequest request){
        var result = service.create(request);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable UUID id){
        var result = service.findDtoById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll(){
        var result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid BookRequest request){
        service.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
