package br.com.elotech.libraryapi.mappers;

import br.com.elotech.libraryapi.dtos.BookLoanRequest;
import br.com.elotech.libraryapi.dtos.BookLoanResponse;
import br.com.elotech.libraryapi.enums.StatusLoan;
import br.com.elotech.libraryapi.models.Book;
import br.com.elotech.libraryapi.models.BookLoan;
import br.com.elotech.libraryapi.models.User;

import java.util.Objects;

public class BookLoanMapper {
    private BookLoanMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static BookLoan toEntity(BookLoanRequest request, Book book, User user) {
        return BookLoan
                .builder()
                .book(book)
                .user(user)
                .returnDate(request.returnDate())
                .status(StatusLoan.LOANED)
                .build();
    }

    public static BookLoanResponse toResponse(BookLoan bookLoan) {
        return new BookLoanResponse(
                bookLoan.getId(),
                Objects.nonNull(bookLoan.getBook()) ? bookLoan.getBook().getId() : null,
                Objects.nonNull(bookLoan.getBook()) ? bookLoan.getBook().getTitle() : null,
                Objects.nonNull(bookLoan.getUser()) ? bookLoan.getUser().getId() : null,
                Objects.nonNull(bookLoan.getUser()) ? bookLoan.getUser().getName() : null,
                bookLoan.getCreatedAt(),
                bookLoan.getReturnDate(),
                bookLoan.getUpdatedAt(),
                bookLoan.getStatus().getDescription()
        );
    }
}
