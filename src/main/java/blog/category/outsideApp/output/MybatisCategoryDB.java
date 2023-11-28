package blog.category.outsideApp.output;

import blog.category.insideApp.port.input.DtoForResponse.CategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MybatisCategoryDB {
    /*
        - 카테고리별 아티클 갯수 통계 쿼리
    */
    @Select("SELECT " +
            "c.category_title AS categoryTitle, " +
            "c.category_id AS categoryId, " +
            "c.c_order AS c_order, " +
            "c.parents_id AS parents, " +
            "COALESCE(COUNT(p.post_id),0) AS count " +
            "FROM category c " +
            "LEFT JOIN " + "post p ON p.category_id = c.category_id OR p.category_id = c.parents_id " +
            "GROUP BY c.category_title, c.category_id, c.c_order, c.parents_id"
    )
    List<CategoryDto> getCategoryCount();
}
