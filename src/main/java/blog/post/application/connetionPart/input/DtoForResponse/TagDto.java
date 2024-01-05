package blog.post.application.connetionPart.input.DtoForResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {
    private String tagName;
    public TagDto() {}
    public TagDto(String name) {
        this.tagName = name;
    }
}
