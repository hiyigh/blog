package blog.comment.outsideApp.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentForm {
    @NotBlank(message = "댓글 내용을 작성해주세요")
    @Size(min = 1,max = 250, message = "댓글은 255자 이내로 작성해주세요")
    private String content;
    private boolean secret;
}
