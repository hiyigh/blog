package blog.post.insideApp.connetionPart.input.DtoForResponse;

import lombok.Getter;
import lombok.Setter;
import blog.category.domain.Category;
import java.util.List;

@Getter
@Setter
public class PostDtoForEdit {

    private Long postId;
    private String postTitle;
    private String postContent;
    private Long postHits;
    private String thumbnailUrl;
    private List<String> postTagList;
    private Category category;
}
