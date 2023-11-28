package blog.post.outsideApp.output;

import blog.post.domain.Tag;
import blog.post.insideApp.connetionPart.output.TagMethodForConnectDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class TagDbAdapter implements TagMethodForConnectDB {
    private final JpaTagsDB jpaTagsDB;
    @Override
    public List<Tag> findAll() {
        return jpaTagsDB.findAll();
    }
}
