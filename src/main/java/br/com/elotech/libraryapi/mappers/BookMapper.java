package br.com.elotech.libraryapi.mappers;

import br.com.elotech.libraryapi.dtos.BookRequest;
import br.com.elotech.libraryapi.dtos.BookResponse;
import br.com.elotech.libraryapi.models.Book;
import br.com.elotech.libraryapi.models.Category;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class BookMapper {

    public static Book toEntity(BookRequest request, Category category){
        var book = new Book();
        BeanUtils.copyProperties(request, book);
        book.setCategory(category);
        return book;
    }

    public static BookResponse toResponse(Book book){
        var response = new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor(),
                book.getPublishedDate(),
                Objects.nonNull(book.getCategory()) ? book.getCategory().getId() : null,
                Objects.nonNull(book.getCategory()) ? book.getCategory().getName() : null,
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
        return response;
    }
}
