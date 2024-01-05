package blog.post.application.connetionPart.input.DtoForResponse;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import static blog.shared.utils.MarkDownUtils.getHtmlRenderer;
import static blog.shared.utils.MarkDownUtils.getParser;

// 메인 화면 렌더링용 아티클 DTO

@Getter
@Setter
public class PostDtoForBox {
    private Long postId;
    private String postTitle;
    private String postContent;
    private String thumbnailUrl;
    private LocalDateTime createdDate;

    public void parseAndRenderForView() {
        // bring this.content and substring under 300 length
        // need parser function
        this.postContent = Jsoup.parse(getHtmlRenderer().render(getParser().parse(this.postContent))).text();
        if(postContent.length()>300) {
            postContent = postContent.substring(0, 300);
        }
    }
}
