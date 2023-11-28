package blog.comment.insideApp.port.input;

import blog.comment.insideApp.port.input.Dto.CommentDto;
import blog.comment.insideApp.port.input.Dto.CommentDtoForLayout;

import java.util.List;

public interface CommentMethod {
    // business logic
    void savePComment(String content, boolean secret, Long memberId, Long postId);
    void saveCComment(String content, boolean secret, Long memberId, Long postId, Long parentId);
    void deleteComment(Long commentId);
    //look up data
    List<CommentDto> getCommentList(Long articleId);
    List<CommentDtoForLayout> recentCommentListForLayout();
}
