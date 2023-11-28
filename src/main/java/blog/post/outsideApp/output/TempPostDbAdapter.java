package blog.post.outsideApp.output;

import blog.post.domain.TempPost;
import blog.post.insideApp.connetionPart.output.TempPostMethodForConnectDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TempPostDbAdapter implements TempPostMethodForConnectDB {
    private final JpaTempPostDB jpaTempPostDB;
    @Override
    public void save(TempPost tempPost) {
        jpaTempPostDB.save(tempPost);
    }

    @Override
    public void delete(TempPost tempPost) {
        jpaTempPostDB.delete(tempPost);
    }
    @Override
    public Optional<TempPost> findById(long id) {
        return jpaTempPostDB.findById(id);
    }
}
