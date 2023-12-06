package blog.category.outsideApp.input;

import blog.category.insideApp.port.input.CategoryMethod;
import blog.category.insideApp.port.input.DtoForResponse.CategoryDto;
import blog.category.insideApp.port.input.DtoForResponse.CategoryViewForLayout;
import blog.category.insideApp.port.output.CategoryMethodForConnectDB;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryMethod categoryMethod;
    private final CategoryMethodForConnectDB categoryMethodForConnectDB;
    private final CategoryValidator categoryValidator;
    @GetMapping("/category/edit")
    public String editCategoryForm(Model model){
        var categoryDtoList = categoryMethod.getCategoryCountList(); // 카테고리별 post 개수 count 등록 후 반환
        var copyDtoList = new ArrayList<>(List.copyOf(categoryDtoList)); // copy categoryList

        CategoryViewForLayout categoryViewForLayout = CategoryViewForLayout.from(categoryDtoList);

        model.addAttribute("categoryDtoForEdit", copyDtoList);
        model.addAttribute("categoryView", categoryViewForLayout);

        return "admin/categoryEdit";
    }
    @PostMapping("/category/edit")
    @ResponseBody String editCategory(@RequestBody List<CategoryDto> categoryDtoList, Errors errors) {
        CategoryDto baseCategoryDto = new CategoryDto();
        if (categoryDtoList.isEmpty() || categoryDtoList == null) {
            baseCategoryDto.setCategoryTitle("base");
            categoryDtoList.add(baseCategoryDto);
        }
        categoryValidator.validate(categoryDtoList, errors);
        categoryMethod.changeCategory(categoryDtoList);
        return "변경 성공";
    }
}
