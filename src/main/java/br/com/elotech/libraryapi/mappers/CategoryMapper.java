package br.com.elotech.libraryapi.mappers;

import br.com.elotech.libraryapi.dtos.CategoryRequest;
import br.com.elotech.libraryapi.dtos.CategoryResponse;
import br.com.elotech.libraryapi.models.Category;
import org.springframework.beans.BeanUtils;

public class CategoryMapper {
    private CategoryMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static Category toEntity(CategoryRequest request){
        var category = new Category();
        BeanUtils.copyProperties(request, category);
        return category;
    }

    public static CategoryResponse toResponse(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
