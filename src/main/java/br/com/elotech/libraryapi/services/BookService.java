package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.BookRequest;
import br.com.elotech.libraryapi.dtos.BookResponse;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.mappers.BookMapper;
import br.com.elotech.libraryapi.models.Book;
import br.com.elotech.libraryapi.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final CategoryService categoryService;

    public Book create(BookRequest request){
        var category = categoryService.findById(request.categoryId());
        var book = BookMapper.toEntity(request, category);
        try {
            return repository.save(book);
        }catch (Exception e){
            log.error("Error on create book {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao criar o livro. Verifique os dados enviados");
        }
    }

    public Book findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotfoundException("Livro não encontrado"));
    }

    public BookResponse findDtoById(UUID id) {
        var book = this.findById(id);
        return BookMapper.toResponse(book);
    }

    public List<BookResponse> findAll() {
        return repository.findAll().stream().map(BookMapper::toResponse).toList();
    }

    public void update(UUID id, BookRequest request) {
        var book = this.findById(id);
        var category = categoryService.findById(request.categoryId());
        BeanUtils.copyProperties(request, book);
        book.setCategory(category);
        try {
            repository.save(book);
        } catch (Exception e) {
            log.error("Error on update book {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao atualizar o livro. Verifique os dados enviados");
        }
    }

    public void delete(UUID id) {
        var book = this.findById(id);
        try {
            repository.delete(book);
        } catch (Exception e) {
            log.error("Error on delete book {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao deletar o livro. Verifique se há empréstimos associados a ele");
        }
    }
}
