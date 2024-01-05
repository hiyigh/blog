package blog.post.application.connetionPart.input.request;

import blog.post.adapter.input.PostInputForm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateCommand { // 실제 외부에서 데이터가 입력됏을 때 데이터를 저장하기 위한 메서드 작성
    private Long member_id;
    private String postTitle;
    private String postContent;
    private String thumbnailUrl;
    private String category;
    private String tag;
    private String hits;

    public PostCreateCommand() {}
    public static PostCreateCommand from(PostInputForm postInputForm, Long member_id) {
        return new PostCreateCommand(member_id, postInputForm.getPostTitle(), postInputForm.getPostContent(),
                postInputForm.getThumbnailUrl(), postInputForm.getCategoryTitle(), postInputForm.getTags(), postInputForm.getToc());
    }
}
