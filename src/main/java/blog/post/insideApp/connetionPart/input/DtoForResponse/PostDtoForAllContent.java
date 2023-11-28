package blog.post.insideApp.connetionPart.input.DtoForResponse;

import lombok.Getter;
import lombok.Setter;
import blog.category.domain.Category;
import java.time.LocalDateTime;
import java.util.List;

import static blog.shared.utils.MarkDownUtils.getHtmlRenderer;
import static blog.shared.utils.MarkDownUtils.getParser;

@Getter
@Setter
public class PostDtoForAllContent {
    private Long postId;
    private String member;
    private String postTitle;
    private String postContent;
    private Long postHits;
    private String thumbnailUrl;
    private List<String> postTagList;
    private Category category;
    private LocalDateTime createdDate;

    public void parseAndRenderForView() {
        this.postContent = getHtmlRenderer().render(getParser().parse(this.postContent));
    }
}
