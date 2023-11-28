package blog.category.insideApp.runPart;

import blog.category.domain.Category;
import blog.category.insideApp.port.input.CategoryMethod;
import blog.category.insideApp.port.input.DtoForResponse.CategoryDto;
import blog.category.insideApp.port.input.DtoForResponse.CategoryViewForLayout;
import blog.category.insideApp.port.output.CategoryMethodForConnectDB;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryMethod {
    private final CategoryMethodForConnectDB categoryMethodForConnectDB;
    private final CategoryDtoMapper categoryDtoMapper;

    @Transactional
    @Override
    public void changeCategory(List<CategoryDto> categoryDtoList) {
        List<Category> existingList = categoryMethodForConnectDB.findAll();
        for (int i = 0; i < existingList.size(); ++i) {
            categoryMethodForConnectDB.deleteById(existingList.get(i).getCategoryId());
        }
        for (CategoryDto categoryDto : categoryDtoList) {
            saveCategory(categoryDto);
        }
    }
    private void saveCategory(CategoryDto categoryDto) {
        Category newCategory = Category.builder()
                .title(categoryDto.getCategoryTitle())
                .cOrder(categoryDto.getCOrder())
                .parents(categoryDto.getParents())
                .child(categoryDto.getChild())
                .build();
        categoryMethodForConnectDB.save(newCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryMethodForConnectDB.findAll();
    }

    @Override
    public Optional<Category> findCategoryByTitle(String title) {
        return categoryMethodForConnectDB.findByTitle(title);
    }
    @Cacheable(value = "layoutCaching", key = "0")
    @Override
    public CategoryViewForLayout getCategoryViewForLayout() {
        return CategoryViewForLayout.from(categoryMethodForConnectDB.getCategoryCount());
    }

    @Override
    public List<CategoryDto> getCategoryCountList() {
        return categoryMethodForConnectDB.getCategoryCount();
    }

    @Override
    public List<CategoryDto> findAllCategoryDto() {
        return categoryMethodForConnectDB.findAll().stream().map(categoryDtoMapper::categoryDto).collect(Collectors.toList());
    }
}
