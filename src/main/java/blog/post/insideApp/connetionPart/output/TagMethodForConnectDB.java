package blog.post.insideApp.connetionPart.output;

import blog.post.domain.Tag;

import java.util.List;

public interface TagMethodForConnectDB {
    List<Tag> findAll();
}
