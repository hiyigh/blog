package blog.category.application.port.input;

import blog.category.application.port.input.DtoForResponse.CategoryDto;
import blog.category.application.port.input.DtoForResponse.CategoryViewForLayout;
import blog.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryMethod {
    void changeCategory (List<CategoryDto> categoryDtoList);
    List<Category> getAllCategories();
    Optional<Category> findCategoryByTitle(String title);
    CategoryViewForLayout getCategoryViewForLayout();
    List<CategoryDto> getCategoryCountList(); // 같은 카테고리가 몇 개 있는지
    List<CategoryDto> findAllCategoryDto();
    void saveCategory(CategoryDto categoryDto);
}
