package blog.category.application.runPart;

import blog.category.domain.Category;
import blog.category.application.port.input.DtoForResponse.CategoryDto;
import org.mapstruct.*;

@Mapper(
componentModel = "spring",
injectionStrategy = InjectionStrategy.CONSTRUCTOR,
unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CategoryDtoMapper {
    @Mappings({
            @Mapping(target = "count",ignore = true),
    })
    CategoryDto categoryDto(Category category);
}
