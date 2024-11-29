package br.com.elotech.libraryapi.repositories;

import br.com.elotech.libraryapi.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {
}
