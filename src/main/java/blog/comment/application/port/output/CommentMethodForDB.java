package blog.comment.application.port.output;

import blog.comment.domain.Comment;
import blog.post.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CommentMethodForDB {
    void saveComment(Comment comment);
    void deleteComment(Long commentId);
    Optional<Comment> findById(Long parentId);
    List<Comment> findCommentsByPostId(Long postId);
    int countCommentsByPostAndTier(Post post, int tier);
    List<Comment> findTop5ByOrderByCommentIdDesc();
}
