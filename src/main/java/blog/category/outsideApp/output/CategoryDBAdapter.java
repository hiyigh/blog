package blog.category.outsideApp.output;

import blog.category.domain.Category;
import blog.category.insideApp.port.input.DtoForResponse.CategoryDto;
import blog.category.insideApp.port.output.CategoryMethodForConnectDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class CategoryDBAdapter implements CategoryMethodForConnectDB {
    private final JpaCategoryDB jpaCategoryDB;
    private final MybatisCategoryDB mybatisCategoryDB;

    @Override
    public void save(Category category) {
        jpaCategoryDB.save(category);
    }

    @Override
    public void deleteById(Long categoryId) {
        jpaCategoryDB.deleteById(categoryId);
    }

    @Override
    public Optional<Category> findByTitle(String title) {
        return jpaCategoryDB.findByCategoryTitle(title);
    }

    @Override
    public List<Category> findAll() {
        return jpaCategoryDB.findAll();
    }

    @Override
    public List<CategoryDto> getCategoryCount() {
        return mybatisCategoryDB.getCategoryCount();
    }
}
