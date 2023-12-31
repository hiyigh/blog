package blog.post.application.runPart;

import blog.post.application.connetionPart.input.DtoForResponse.TagDto;
import blog.post.application.connetionPart.input.TagMethod;
import blog.post.application.connetionPart.output.TagMethodForConnectDB;
import blog.post.domain.Post;
import blog.post.domain.PostTagList;
import blog.post.domain.Tag;
import blog.post.adapter.output.JpaPostTagListDB;
import blog.post.adapter.output.JpaTagsDB;
import blog.shared.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService implements TagMethod {
    private final TagMethodForConnectDB tagMethodForConnectDB;
    private final PostDtoMapper postDtoMapper;

    private final JpaPostTagListDB jpaPostTagListDB;
    private final JpaTagsDB jpaTagsDB;

    @Override
    public List<TagDto> findAllTagDtos() {
        List<Tag> tagList = tagMethodForConnectDB.findAll();
        return tagList.stream().map(postDtoMapper::tag).collect(Collectors.toList());
    }

    @Override // post 에 저장된 tag 이름을 가져와서 list 로 만든다. // post 하나에 tag list 를 저장할 수 없나?
    public void createTagAndTagList(String tagName, Post post) {
        List<Map<String, String>> tagDtoList = MapperUtils.getGson().fromJson(tagName, ArrayList.class);
        for (Map<String, String> tagDto : tagDtoList) {
            Tag tag = addTagDto(tagDto);
            jpaPostTagListDB.save(new PostTagList(post, tag));
        }
    }

    private Tag addTagDto(Map<String, String> tagDto) {
        return jpaTagsDB.findByTagName(tagDto.get("value")).orElseGet(()->jpaTagsDB.save(new Tag(tagDto.get("value"))));
    }

    @Override
    public void deleteAllTag(Post post) {
        jpaPostTagListDB.deleteByPost(post);
    }
}
