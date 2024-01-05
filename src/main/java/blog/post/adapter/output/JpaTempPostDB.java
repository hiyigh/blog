package blog.post.adapter.output;

import blog.post.domain.TempPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTempPostDB extends JpaRepository<TempPost, Long> {
}
