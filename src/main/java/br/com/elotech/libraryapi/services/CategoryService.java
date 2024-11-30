package br.com.elotech.libraryapi.services;

import br.com.elotech.libraryapi.dtos.CategoryRequest;
import br.com.elotech.libraryapi.dtos.CategoryResponse;
import br.com.elotech.libraryapi.exceptions.DataIntegrityException;
import br.com.elotech.libraryapi.exceptions.ObjectNotfoundException;
import br.com.elotech.libraryapi.mappers.CategoryMapper;
import br.com.elotech.libraryapi.models.Category;
import br.com.elotech.libraryapi.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category create(CategoryRequest request){
        var category = CategoryMapper.toEntity(request);
        try {
            return repository.save(category);
        }catch (Exception e){
            log.error("Error on create category {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao criar a categoria. Verifique os dados enviados");
        }
    }

    public Category findById(UUID id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotfoundException("Categoria não encontrada"));
    }

    public CategoryResponse findDtoById(UUID id){
        var category = this.findById(id);
        return CategoryMapper.toResponse(category);
    }

    public List<CategoryResponse> findAll(){
        return repository.findAll().stream().map(CategoryMapper::toResponse).toList();
    }

    public void update(UUID id, CategoryRequest request){
        var category = this.findById(id);
        category.setName(request.name());
        try {
            repository.save(category);
        }catch (Exception e){
            log.error("Error on update category {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao atualizar a categoria. Verifique os dados enviados");
        }
    }

    public void delete(UUID id){
        var category = this.findById(id);
        try {
            repository.delete(category);
        }catch (Exception e){
            log.error("Error on delete category {}", e.getMessage());
            throw new DataIntegrityException("Houve um erro ao deletar a categoria. Verifique se a categoria não está sendo usada em algum livro");
        }
    }
}
