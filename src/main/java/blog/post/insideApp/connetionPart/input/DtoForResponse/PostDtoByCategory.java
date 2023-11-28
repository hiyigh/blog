package blog.post.insideApp.connetionPart.input.DtoForResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 카테고리 제목과 카테고리 식별 번호?
public class PostDtoByCategory {
    private String postTitle;
    private Long postId;

}
