package blog.comment.outsideApp.output;

import blog.comment.domain.Comment;
import blog.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaCommentDB extends JpaRepository<Comment, Long> {
    void deleteById(Long commentId);
    @Query("select c from Comment c " + "where post_id =:postId")
    List<Comment> findCommentsByPostId(@Param("postId") Long postId);
    // 특정 아티클의 부모 댓글 총 갯수
    int countCommentsByPostAndTier(Post post, int tier);

    // 전체 댓글중 최신 댓글 5개
    List<Comment> findTop5ByOrderByCommentIdDesc();
}
