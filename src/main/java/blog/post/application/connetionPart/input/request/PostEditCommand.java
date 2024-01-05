package blog.post.application.connetionPart.input.request;

import blog.post.adapter.input.PostInputForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostEditCommand {
    private Long post_id;
    private String postTitle;
    private String postContent;
    private String categoryTitle;
    private String tag;
    private String thumbnailUrl;
    private String hits;

    public static PostEditCommand from (Long post_id, PostInputForm postInputForm) {
        return new PostEditCommand(post_id, postInputForm.getPostTitle(), postInputForm.getPostContent(), postInputForm.getCategoryTitle()
                ,postInputForm.getTags(), postInputForm.getThumbnailUrl(), postInputForm.getToc());
    }
}
