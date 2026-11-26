package qr.system.mapper;

import qr.system.entity.Category;
import qr.system.request.CategoryRequest;
import qr.system.response.CategoryDto;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        return category;
    }

    public static CategoryDto toResponseDto(Category entity) {
        if (entity == null) {
            return null;
        }

        return CategoryDto.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .build();
    }
}
