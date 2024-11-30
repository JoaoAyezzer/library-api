package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.UserRequest;
import br.com.elotech.libraryapi.dtos.UserResponse;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.mappers.UserMapper;
import br.com.elotech.libraryapi.models.User;
import br.com.elotech.libraryapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User create(UserRequest request){
        var user = UserMapper.toEntity(request);
        try {
            return repository.save(user);
        }catch (Exception e){
            log.error("Error on create user {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao criar o usuário. Verifique os dados enviados");
        }
    }

    public User findById(UUID id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotfoundException("Usuário não encontrado"));
    }

    public UserResponse findDtoById(UUID id){
        var user = this.findById(id);
        return UserMapper.toResponse(user);
    }

    public List<UserResponse> findAll(){
        return repository.findAll().stream().map(UserMapper::toResponse).toList();
    }

    public void update(UUID id, UserRequest request){
        var user = this.findById(id);
        BeanUtils.copyProperties(request, user);
        try {
            repository.save(user);
        }catch (Exception e){
            log.error("Error on update user {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao atualizar o usuário. Verifique os dados enviados");
        }
    }

    public void delete(UUID id){
        var user = this.findById(id);
        try {
            repository.delete(user);
        }catch (Exception e){
            log.error("Error on delete user {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao deletar o usuário. Verifique há empréstimos vinculados a este usuário");
        }
    }
}
