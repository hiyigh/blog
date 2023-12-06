package blog.category.insideApp.port.output;

import blog.category.domain.Category;
import blog.category.insideApp.port.input.DtoForResponse.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryMethodForConnectDB { // service 에서 db 에 연결할 때 사용될 method
    void save (Category category);
    void deleteById(Long categoryId);
    Optional<Category> findByTitle(String title);
    List<Category> findAll();
    List<CategoryDto> getCategoryCount();

    void updateTitle(Long categoryId, String compareTitle);
}
