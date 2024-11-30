package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.CategoryRequest;
import br.com.elotech.libraryapi.dtos.CategoryResponse;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.models.Category;
import br.com.elotech.libraryapi.repositories.CategoryRepository;
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

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private CategoryRequest categoryRequest;
    private UUID categoryId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryId = UUID.randomUUID();
        category = Category.builder()
                .name("Romance")
                .build();
        category.setId(categoryId);

        categoryRequest = new CategoryRequest("Romance");
    }

    @Test
    @DisplayName("Deve criar uma categoria com sucesso")
    void shouldCreateCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category createdCategory = categoryService.create(categoryRequest);

        assertNotNull(createdCategory);
        assertEquals(category.getId(), createdCategory.getId());
        assertEquals(category.getName(), createdCategory.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a criação de categoria falhar")
    void shouldThrowExceptionWhenCreateCategoryFails() {
        when(categoryRepository.save(any(Category.class))).thenThrow(RuntimeException.class);

        DataIntegrityException exception = assertThrows(DataIntegrityException.class,
                () -> categoryService.create(categoryRequest));

        assertEquals("Houve um erro ao criar a categoria. Verifique os dados enviados", exception.getMessage());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Deve buscar Categoria por id com sucesso")
    void shouldFindCategoryById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.findById(categoryId);

        assertNotNull(foundCategory);
        assertEquals(category.getId(), foundCategory.getId());
        assertEquals(category.getName(), foundCategory.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a categoria não for encontrada")
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        ObjectNotfoundException exception = assertThrows(ObjectNotfoundException.class,
                () -> categoryService.findById(categoryId));

        assertEquals("Categoria não encontrada", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Deve buscar Categoria por id e retornar DTO com sucesso")
    void shouldFindDtoById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryResponse response = categoryService.findDtoById(categoryId);

        assertNotNull(response);
        assertEquals(category.getId(), response.id());
        assertEquals(category.getName(), response.name());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Deve buscar todas as categorias com sucesso")
    void shouldFindAllCategory() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryResponse> responses = categoryService.findAll();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(category.getId(), responses.get(0).id());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar uma categoria com sucesso")
    void shouldUpdateCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.update(categoryId, categoryRequest);

        assertEquals(categoryRequest.name(), category.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a atualização de categoria falhar")
    void shouldDeleteCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        categoryService.delete(categoryId);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    @DisplayName("Deve lançar exceção quando a exclusão de categoria falhar")
    void shouldThrowExceptionWhenDeleteFails() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        doThrow(RuntimeException.class).when(categoryRepository).delete(category);

        DataIntegrityException exception = assertThrows(DataIntegrityException.class,
                () -> categoryService.delete(categoryId));

        assertEquals("Houve um erro ao deletar a categoria. Verifique se a categoria não está sendo usada em algum livro",
                exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(category);
    }
}
