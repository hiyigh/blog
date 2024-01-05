package blog.category.application.port.input.DtoForResponse;

import blog.category.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDto {
    private Long categoryId;
    @NotBlank(message = "카테고리명은 공백일 수 없습니다.")
    private String categoryTitle;
    private Category parents;
    private List<Category> child;
    private int cOrder;
    private int count;
}
