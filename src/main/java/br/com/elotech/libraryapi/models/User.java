package br.com.elotech.libraryapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<BookLoan> bookLoans;
}
