package blog.post.outsideApp.output;

import blog.post.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTagsDB extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String name);
}
