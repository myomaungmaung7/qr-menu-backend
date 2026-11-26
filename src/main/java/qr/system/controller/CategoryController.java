package qr.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qr.system.request.CategoryRequest;
import qr.system.response.ApiResponse;
import qr.system.response.CategoryDto;
import qr.system.service.CategoryService;
import qr.system.utils.ResponseUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryRequest request) {

        CategoryDto categoryDto = categoryService.createCategory(request);
        return ResponseUtil.created("Category created successfully.", categoryDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategories() {

        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseUtil.ok("Categories fetched successfully.", categories);
    }
}
