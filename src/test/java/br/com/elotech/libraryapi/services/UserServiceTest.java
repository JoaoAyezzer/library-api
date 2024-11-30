package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.UserRequest;
import br.com.elotech.libraryapi.dtos.UserResponse;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.models.User;
import br.com.elotech.libraryapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User user;
    private UserRequest userRequest;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        user = User.builder()
                .name("João Ayezzer")
                .email("joaoayezzerp@gmail.com")
                .phone("44999888822")
                .build();
        user.setId(userId);
        userRequest = new UserRequest("João Ayezzer", "joaoayezzerp@gmail.com", "44999888822");
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void shouldCreateUser() {
        when(repository.save(any(User.class))).thenReturn(user);

        User createdUser = service.create(userRequest);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());

        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a criação do usuário falhar")
    void shouldThrowExceptionWhenCreateUserFails() {
        when(repository.save(any(User.class))).thenThrow(RuntimeException.class);

        assertThrows(DataIntegrityException.class, () -> service.create(userRequest));
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve encontrar usuário por id com sucesso")
    void shouldFindUserById() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = service.findById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        verify(repository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotfoundException.class, () -> service.findById(userId));
        verify(repository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Deve encontrar usuário por id e retornar DTO com sucesso")
    void shouldFindDtoById() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse response = service.findDtoById(userId);

        assertNotNull(response);
        assertEquals(userId, response.id());
        verify(repository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Deve buscar todos os usuarios com sucesso")
    void shouldFindAllUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<UserResponse> users = service.findAll();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void shouldUpdateUser() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);

        assertDoesNotThrow(() -> service.update(userId, userRequest));
        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a atualização falhar")
    void shouldThrowExceptionWhenUpdateFails() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenThrow(RuntimeException.class);

        assertThrows(DataIntegrityException.class, () -> service.update(userId, userRequest));
        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void shouldDeleteUser() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> service.delete(userId));
        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a deleção falhar")
    void shouldThrowExceptionWhenDeleteFails() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        doThrow(RuntimeException.class).when(repository).delete(user);

        assertThrows(DataIntegrityException.class, () -> service.delete(userId));
        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).delete(user);
    }
}
