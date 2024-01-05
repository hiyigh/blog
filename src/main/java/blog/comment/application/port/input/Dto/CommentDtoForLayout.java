package blog.comment.application.port.input.Dto;

import lombok.Getter;
import lombok.Setter;

/*
    - 레이아웃 노출용 댓글 DTO
    commentDto 를 layout 용 commentDto 로 변경
*/
@Getter
@Setter
public class CommentDtoForLayout {
    private Long id;
    private Long postId;
    private String comment;
    private boolean secret;

    public CommentDtoForLayout(Long id, Long postId, String comment, boolean secret) {
        this.id = id;
        this.secret = secret;
        this.postId = postId;
        this.comment = comment;
    }
}
