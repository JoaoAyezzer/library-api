package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.BookResponse;
import br.com.elotech.libraryapi.mappers.BookMapper;
import br.com.elotech.libraryapi.models.BookLoan;
import br.com.elotech.libraryapi.repositories.BookLoanRepository;
import br.com.elotech.libraryapi.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final BookRepository bookRepository;
    private final BookLoanRepository bookLoanRepository;

    public List<BookResponse> recommendBooksForUser(UUID id){
        //Busca todos os livros que o usuário já pegou emprestado
        List<BookLoan> bookLoans = bookLoanRepository.findAllByUserId(id);

        //Filtra todos os ids dos livros que o usuário já pegou emprestado
        Set<UUID> bookIds = bookLoans
                .stream()
                .map(loan -> loan.getBook().getId())
                .collect(Collectors.toSet());

        //Filtra todos os ids das categorias dos livros que o usuário já pegou emprestado
        Set<UUID> borrowedCategoryIds = bookLoans
                .stream()
                .map(loan -> loan.getBook().getCategory().getId())
                .collect(Collectors.toSet());

        return bookRepository.findByCategoryIdInAndIdNotIn(borrowedCategoryIds, bookIds).stream().map(BookMapper::toResponse).toList();
    }
}
