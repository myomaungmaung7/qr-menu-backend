package qr.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qr.system.entity.Category;
import qr.system.exceptions.DuplicateResourceException;
import qr.system.exceptions.ResourceNotFoundException;
import qr.system.mapper.CategoryMapper;
import qr.system.repository.CategoryRepository;
import qr.system.request.CategoryRequest;
import qr.system.response.CategoryDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(@Valid CategoryRequest request) {

        log.info("Attempting to create a new category with name: {}", request.getCategoryName());

        if (categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
            log.warn("Category creation failed: Category with name '{}' already exists.", request.getCategoryName());
            throw new DuplicateResourceException("Category with name '" + request.getCategoryName() + "' already exists.");
        }

        Category category = CategoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);

        log.info("Successfully created category with ID: {}", savedCategory.getId());
        return CategoryMapper.toResponseDto(savedCategory);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        log.info("Fetching all category record from the database.");
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            log.warn("No categories found in the database.");
            throw new ResourceNotFoundException("No categories found.");
        }

        return categories.stream()
                .map(CategoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
