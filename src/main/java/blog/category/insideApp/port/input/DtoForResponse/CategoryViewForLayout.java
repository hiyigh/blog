package blog.category.insideApp.port.input.DtoForResponse;

import blog.category.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryViewForLayout {
    private Long categoryId;
    private String categoryTitle;
    private Category parents;
    private int cOrder;
    private int count; // categoryTitle 별로 가지고 있는 post 개수
    private List<CategoryViewForLayout> categoryViewTreeList = new ArrayList<>();

    private CategoryViewForLayout() {
    }

    public static CategoryViewForLayout from(List<CategoryDto> source) {
        if (source.isEmpty()) return new CategoryViewForLayout();
        return makeCategoryChildList(source);
    }

    private static CategoryViewForLayout makeCategoryChildList(List<CategoryDto> source) {
        CategoryViewForLayout root = new CategoryViewForLayout();
        for (int i = 0; i < source.size(); ++i) {
            CategoryViewForLayout changeSource = changeToView(source.get(i));
            root.getCategoryViewTreeList().add(changeSource);
        }
        return root;
    }

    private static CategoryViewForLayout changeToView(CategoryDto categoryDto) {
        CategoryViewForLayout view = new CategoryViewForLayout();
        view.setCategoryId(categoryDto.getCategoryId());
        view.setCategoryTitle(categoryDto.getCategoryTitle());
        view.setParents(categoryDto.getParents());
        view.setCount(categoryDto.getCount());
        view.setCOrder(categoryDto.getCOrder());
        return view;
    }
}
