package blog.comment.application.port.input;

import blog.comment.application.port.input.Dto.CommentDto;
import blog.comment.application.port.input.Dto.CommentDtoForLayout;

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
