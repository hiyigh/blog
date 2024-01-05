package blog.post.adapter.output;

import blog.post.domain.Tag;
import blog.post.application.connetionPart.output.TagMethodForConnectDB;
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
