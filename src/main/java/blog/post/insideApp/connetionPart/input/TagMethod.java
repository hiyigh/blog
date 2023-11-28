package blog.post.insideApp.connetionPart.input;

import blog.post.domain.Post;
import blog.post.insideApp.connetionPart.input.DtoForResponse.TagDto;

import java.util.List;

public interface TagMethod {
    List<TagDto> findAllTagDtos();
    void createTagAndTagList(String tagName, Post post);
    void deleteAllTag(Post post);
}
