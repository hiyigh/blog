package blog.post.outsideApp.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Setter
@Getter
public class PostInputForm {
    @NotBlank(message = "제목을 입력해주세요")
    private String postTitle;
    @NotBlank(message = "내용을 입력해주세요")
    private String postContent;
    @NotBlank(message = "카테고리를 입력해주세요")
    private String categoryTitle;
    @NotBlank(message = "태그를 하나이상 입력해주세요")
    private String tags;
    private String thumbnailUrl;
    private String toc;
}
