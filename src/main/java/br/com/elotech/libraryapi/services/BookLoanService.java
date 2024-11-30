package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.BookLoanRequest;
import br.com.elotech.libraryapi.dtos.BookLoanResponse;
import br.com.elotech.libraryapi.enums.StatusLoan;
import br.com.elotech.libraryapi.exceptions.BadRequestException;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.mappers.BookLoanMapper;
import br.com.elotech.libraryapi.models.BookLoan;
import br.com.elotech.libraryapi.repositories.BookLoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookLoanService {
    private final BookLoanRepository repository;
    private final BookService bookService;
    private final UserService userService;

    public BookLoan create(BookLoanRequest request){
        this.validActiveLoan(request.bookId());
        var book = bookService.findById(request.bookId());
        var user = userService.findById(request.userId());
        var bookLoan = BookLoanMapper.toEntity(request, book, user);
        try {
            return repository.save(bookLoan);
        }catch (Exception e){
            log.error("Error on create book loan {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao criar o empréstimo. Verifique os dados enviados");
        }
    }

    private void validActiveLoan(UUID bookId){
        var bookLoans = repository.findAllByBookIdAndStatus(bookId, StatusLoan.LOANED);
        if(!bookLoans.isEmpty()){
            throw new BadRequestException("O livro já está emprestado, não é possível realizar um novo empréstimo");
        }
    }

    public BookLoan findById(UUID id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotfoundException("Empréstimo não encontrado"));
    }

    public BookLoanResponse findDtoById(UUID id){
        var bookLoan = this.findById(id);
        return BookLoanMapper.toResponse(bookLoan);
    }

    public List<BookLoanResponse> findAll(){
        return repository.findAll().stream().map(BookLoanMapper::toResponse).toList();
    }

    public void update(UUID id){
        var bookLoan = this.findById(id);
        bookLoan.setStatus(StatusLoan.RETURNED);
        bookLoan.setReturnDate(LocalDate.now());
        try {
            repository.save(bookLoan);
        }catch (Exception e){
            log.error("Error on update book loan {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao atualizar o empréstimo. Verifique os dados enviados");
        }
    }

    public List<BookLoanResponse> findAllByUserId(UUID userId){
        return repository.findAllByUserId(userId).stream().map(BookLoanMapper::toResponse).toList();
    }
}
