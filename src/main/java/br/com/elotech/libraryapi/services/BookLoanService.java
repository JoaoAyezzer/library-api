package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.repositories.BookLoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLoanService {
    private final BookLoanRepository repository;
}
