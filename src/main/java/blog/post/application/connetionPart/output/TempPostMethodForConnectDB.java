package blog.post.application.connetionPart.output;

import blog.post.domain.TempPost;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TempPostMethodForConnectDB {
    void save(TempPost tempPost);
    void delete(TempPost tempPost);
    Optional<TempPost> findById(long l);
}
