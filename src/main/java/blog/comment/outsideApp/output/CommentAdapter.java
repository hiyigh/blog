package blog.comment.outsideApp.output;

import blog.comment.domain.Comment;
import blog.comment.insideApp.port.output.CommentMethodForDB;
import blog.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class CommentAdapter implements CommentMethodForDB {
    private final JpaCommentDB jpaCommentDB;
    @Override
    public void saveComment(Comment comment) {
        jpaCommentDB.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        jpaCommentDB.deleteById(commentId);
    }

    @Override
    public Optional<Comment> findById(Long parentId) {
        return jpaCommentDB.findById(parentId);
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId) {
        return jpaCommentDB.findCommentsByPostId(postId);
    }

    @Override
    public int countCommentsByPostAndTier(Post post, int tier) {
        return jpaCommentDB.countCommentsByPostAndTier(post,tier);
    }

    @Override
    public List<Comment> findTop5ByOrderByCommentIdDesc() {
        return jpaCommentDB.findTop5ByOrderByCommentIdDesc();
    }
}
