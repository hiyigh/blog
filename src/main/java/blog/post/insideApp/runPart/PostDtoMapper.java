package blog.post.insideApp.runPart;

import blog.post.domain.Post;
import blog.post.domain.Tag;
import blog.post.insideApp.connetionPart.input.DtoForResponse.*;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
injectionStrategy = InjectionStrategy.CONSTRUCTOR,
unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PostDtoMapper {
    PostDtoForBox cardBox(Post post);
    
    @Mapping(target = "postTagList",ignore = true)
    PostDtoForEdit edit(Post post);

    @Mappings({
            @Mapping(target = "postTagList",ignore = true),
            @Mapping(source = "category", target = "category"),
            @Mapping(source = "member.memberId", target = "member"),
    })
    PostDtoForAllContent allContent(Post post);

    PostDtoByCategory category(Post post);
    TagDto tag(Tag tag);

}
