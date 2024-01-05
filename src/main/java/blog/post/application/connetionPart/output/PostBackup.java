package blog.post.application.connetionPart.output;

import blog.post.domain.Post;
public interface PostBackup {
    void backup(Post post);
}
