package blog.post.outsideApp.output;

import blog.post.domain.TempPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaTempPostDB extends JpaRepository<TempPost, Long> {
}
