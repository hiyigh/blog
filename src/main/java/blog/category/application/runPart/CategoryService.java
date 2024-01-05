package blog.category.application.runPart;

import blog.category.domain.Category;
import blog.category.application.port.input.CategoryMethod;
import blog.category.application.port.input.DtoForResponse.CategoryDto;
import blog.category.application.port.input.DtoForResponse.CategoryViewForLayout;
import blog.category.application.port.output.CategoryMethodForConnectDB;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryMethod {
    private final CategoryMethodForConnectDB categoryMethodForConnectDB;
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    public void changeCategory(List<CategoryDto> categoryDtoList) {
        List<Category> originCategoryList = categoryMethodForConnectDB.findAll();

        if (originCategoryList.isEmpty()) {
            for (CategoryDto dto : categoryDtoList) {
                saveCategory(dto);
            }
            return;
        }

        for (CategoryDto dto : categoryDtoList) {
            if (dto.getCategoryId() == null) {
                saveCategory(dto);
            } else {
                for (Category origin : originCategoryList) {
                    if (dto.getCategoryId().equals(origin.getCategoryId())) {
                        if (!dto.getCategoryTitle().equals(origin.getCategoryTitle())) {
                            categoryMethodForConnectDB.updateTitle(origin.getCategoryId(), dto.getCategoryTitle());
                        }
                        break;
                    }
                }
            }
        }
        for (Category origin : originCategoryList) {
            boolean notFound = true;
            for (CategoryDto dto : categoryDtoList) {
                if (origin.getCategoryId().equals(dto.getCategoryId())) {
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                categoryMethodForConnectDB.deleteById(origin.getCategoryId());
            }
        }
    }
    private boolean checkCategoryTitle(String compareTitle, String originTitle) {
        if (originTitle.equals(compareTitle)) return true;
        return false;
    }
    public void saveCategory(CategoryDto categoryDto) {
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
    @Cacheable(value = "layoutCaching")
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
