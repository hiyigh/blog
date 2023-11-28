package blog.category.outsideApp.output;

import blog.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface JpaCategoryDB extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryTitle (String title);

    @Modifying
    @Query("delete from Category where category_id = :categoryId")
    void deleteById(Long categoryId);
}
