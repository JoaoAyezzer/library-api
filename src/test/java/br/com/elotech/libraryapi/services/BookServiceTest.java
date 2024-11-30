package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.BookRequest;
import br.com.elotech.libraryapi.dtos.BookResponse;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.models.Book;
import br.com.elotech.libraryapi.models.Category;
import br.com.elotech.libraryapi.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryService categoryService;

    private UUID bookId;
    private UUID categoryId;
    private Book book;
    private Category category;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookId = UUID.randomUUID();
        categoryId = UUID.randomUUID();

        category = Category.builder()
                .name("Fantasia")
                .build();
        category.setId(categoryId);

        book = Book.builder()
                .title("Harry Potter e a Criança Amaldiçoada - Partes Um e Dois")
                .isbn(Set.of("9781781105337", "1781105332"))
                .author(Set.of("J.K. Rowling", "John Tiffany", "Jack Thorne"))
                .publishedDate(LocalDate.now())
                .category(category)
                .build();
        book.setId(bookId);

        bookRequest = new BookRequest(
                "Harry Potter e a Criança Amaldiçoada - Partes Um e Dois",
                Set.of("9781781105337", "1781105332"),
                Set.of("J.K. Rowling", "John Tiffany", "Jack Thorne"),
                LocalDate.now(),
                categoryId
        );
    }

    @Test
    @DisplayName("Deve criar livro com sucesso")
    void shouldCreateBook() {
        when(categoryService.findById(categoryId)).thenReturn(category);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.create(bookRequest);

        assertNotNull(createdBook);
        assertEquals(book.getId(), createdBook.getId());
        verify(categoryService, times(1)).findById(categoryId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a criação do livro falhar")
    void shouldThrowExceptionWhenCreateBookFails() {
        when(categoryService.findById(categoryId)).thenReturn(category);
        when(bookRepository.save(any(Book.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(DataIntegrityException.class, () -> bookService.create(bookRequest));
        verify(categoryService, times(1)).findById(categoryId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve encontrar livro por id com sucesso")
    void shouldFindBookById() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book foundBook = bookService.findById(bookId);

        assertNotNull(foundBook);
        assertEquals(book.getId(), foundBook.getId());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando livro não for encontrado")
    void shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotfoundException.class, () -> bookService.findById(bookId));
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Deve retornar todos os livros com sucesso")
    void shouldFindAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookResponse> books = bookService.findAll();

        assertNotNull(books);
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar livro com sucesso")
    void shouldUpdateBook() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(categoryService.findById(categoryId)).thenReturn(category);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        assertDoesNotThrow(() -> bookService.update(bookId, bookRequest));
        verify(bookRepository, times(1)).findById(bookId);
        verify(categoryService, times(1)).findById(categoryId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a atualização do livro falhar")
    void shouldThrowExceptionWhenUpdateFails() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotfoundException.class, () -> bookService.update(bookId, bookRequest));
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Deve deletar livro com sucesso")
    void shouldDeleteBook() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        assertDoesNotThrow(() -> bookService.delete(bookId));
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a deleção do livro falhar")
    void shouldThrowExceptionWhenDeleteFails() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotfoundException.class, () -> bookService.delete(bookId));
        verify(bookRepository, times(1)).findById(bookId);
    }
}
