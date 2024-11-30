package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.BookLoanRequest;
import br.com.elotech.libraryapi.dtos.BookLoanResponse;
import br.com.elotech.libraryapi.enums.StatusLoan;
import br.com.elotech.libraryapi.exceptions.BadRequestException;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.models.Book;
import br.com.elotech.libraryapi.models.BookLoan;
import br.com.elotech.libraryapi.models.User;
import br.com.elotech.libraryapi.repositories.BookLoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookLoanServiceTest {

    @InjectMocks
    private BookLoanService bookLoanService;

    @Mock
    private BookLoanRepository bookLoanRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    private UUID bookLoanId;
    private UUID bookId;
    private UUID userId;
    private BookLoanRequest bookLoanRequest;
    private BookLoan bookLoan;
    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookLoanId = UUID.randomUUID();
        bookId = UUID.randomUUID();
        userId = UUID.randomUUID();

        book = Book.builder()
                .title("Test Book")
                .build();
        book.setId(bookId);

        user = User.builder()
                .name("John Doe")
                .build();
        user.setId(userId);

        bookLoan = BookLoan.builder()
                .book(book)
                .user(user)
                .status(StatusLoan.LOANED)
                .returnDate(LocalDate.now().plusDays(7))
                .build();
        bookLoan.setId(bookLoanId);

        bookLoanRequest = new BookLoanRequest(bookId, userId, LocalDate.now().plusDays(7));
    }

    @Test
    @DisplayName("Deve criar empréstimo com sucesso")
    void shouldCreateBookLoan() {
        when(bookService.findById(bookId)).thenReturn(book);
        when(userService.findById(userId)).thenReturn(user);
        when(bookLoanRepository.save(any(BookLoan.class))).thenReturn(bookLoan);

        BookLoan createdLoan = bookLoanService.create(bookLoanRequest);

        assertNotNull(createdLoan);
        assertEquals(bookLoan.getId(), createdLoan.getId());
        verify(bookService, times(1)).findById(bookId);
        verify(userService, times(1)).findById(userId);
        verify(bookLoanRepository, times(1)).save(any(BookLoan.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se o livro estiver emprestado")
    void shouldThrowExceptionIfBookIsBorrowed() {
        when(bookLoanRepository.findAllByBookIdAndStatus(bookId, StatusLoan.LOANED)).thenReturn(List.of(bookLoan));

        assertThrows(BadRequestException.class, () -> bookLoanService.create(bookLoanRequest));
        verify(bookLoanRepository, times(1)).findAllByBookIdAndStatus(bookId, StatusLoan.LOANED);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o repositório falhar")
    void shouldThrowExceptionWhenRepositoryFails() {
        when(bookService.findById(bookId)).thenReturn(book);
        when(userService.findById(userId)).thenReturn(user);
        when(bookLoanRepository.save(any(BookLoan.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(DataIntegrityException.class, () -> bookLoanService.create(bookLoanRequest));
        verify(bookLoanRepository, times(1)).save(any(BookLoan.class));
    }

    @Test
    @DisplayName("Deve boscar empréstimo por id com sucesso")
    void shouldFindBookLonById() {
        when(bookLoanRepository.findById(bookLoanId)).thenReturn(Optional.of(bookLoan));

        BookLoan foundBookLoan = bookLoanService.findById(bookLoanId);

        assertNotNull(foundBookLoan);
        assertEquals(bookLoan.getId(), foundBookLoan.getId());
        verify(bookLoanRepository, times(1)).findById(bookLoanId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando empréstimo não for encontrado")
    void shouldThrowExceptionWhenBookLoanNotFound() {
        when(bookLoanRepository.findById(bookLoanId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotfoundException.class, () -> bookLoanService.findById(bookLoanId));
        verify(bookLoanRepository, times(1)).findById(bookLoanId);
    }

    @Test
    @DisplayName("Deve buscar todos os empréstimos com sucesso")
    void shouldFindAllBookLoan() {
        when(bookLoanRepository.findAll()).thenReturn(List.of(bookLoan));

        List<BookLoanResponse> bookLoans = bookLoanService.findAll();

        assertNotNull(bookLoans);
        assertEquals(1, bookLoans.size());
        verify(bookLoanRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar empréstimo com sucesso")
    void shouldUpdateBookLoan() {
        when(bookLoanRepository.findById(bookLoanId)).thenReturn(Optional.of(bookLoan));
        when(bookLoanRepository.save(any(BookLoan.class))).thenReturn(bookLoan);

        assertDoesNotThrow(() -> bookLoanService.update(bookLoanId));

        verify(bookLoanRepository, times(1)).findById(bookLoanId);
        verify(bookLoanRepository, times(1)).save(any(BookLoan.class));
    }

    @Test
    @DisplayName("Deve retornar uma lista de empréstimos por id do usuário")
    void shouldReturnListOfBookLoansByUserId() {
        when(bookLoanRepository.findAllByUserId(userId)).thenReturn(List.of(bookLoan));

        List<BookLoanResponse> bookLoans = bookLoanService.findAllByUserId(userId);

        assertNotNull(bookLoans);
        assertEquals(1, bookLoans.size());
        verify(bookLoanRepository, times(1)).findAllByUserId(userId);
    }
}
