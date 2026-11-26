package qr.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qr.system.entity.Category;

import javax.validation.constraints.NotBlank;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryNameIgnoreCase(@NotBlank(message = "Category name is required") String categoryName);
}
