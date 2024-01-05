package blog.post.application.connetionPart.input;

import blog.post.application.connetionPart.input.DtoForResponse.TagDto;
import blog.post.domain.Post;

import java.util.List;

public interface TagMethod {
    List<TagDto> findAllTagDtos();
    void createTagAndTagList(String tagName, Post post);
    void deleteAllTag(Post post);
}
