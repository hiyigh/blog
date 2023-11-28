package blog.post.insideApp.runPart;

import blog.category.domain.Category;
import blog.category.insideApp.port.input.CategoryMethod;
import blog.post.domain.Post;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoByCategory;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForAllContent;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForBox;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForEdit;
import blog.post.insideApp.connetionPart.input.PostMethod;
import blog.post.insideApp.connetionPart.input.TagMethod;
import blog.post.insideApp.connetionPart.input.request.PostCreateCommand;
import blog.post.insideApp.connetionPart.input.request.PostEditCommand;
import blog.post.insideApp.connetionPart.output.PostMethodForConnectDB;
import blog.shared.exception.ResourceNotFoundException;
import blog.users.domain.Member;
import blog.users.inApp.port.input.MemberMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements PostMethod {
    private final PostMethodForConnectDB postMethodForConnectDB;
    private final MemberMethod memberMethod;
    private final CategoryMethod categoryMethod;
    private final TagMethod tagMethod;
    private final PostDtoMapper postDtoMapper;
    private final int size = 5;

    @Override
    @CacheEvict(value = {"layoutCaching", "seoCaching"}, allEntries = true)
    public Long writePost(PostCreateCommand postCreateCommand) {
        Member writer = memberMethod.findById(postCreateCommand.getMember_id()).orElseThrow(ResourceNotFoundException::new);
        Category category = categoryMethod.findCategoryByTitle(postCreateCommand.getCategory()).orElseThrow(ResourceNotFoundException::new);
        Post newPost = new Post(postCreateCommand.getPostTitle(),
                postCreateCommand.getPostContent(), writer, category, postCreateCommand.getThumbnailUrl());

        postMethodForConnectDB.save(newPost);
        tagMethod.createTagAndTagList(postCreateCommand.getTag(), newPost);
        return newPost.getPostId();
    }

    @Override
    @CacheEvict(value = {"layoutCaching", "seoCaching"}, allEntries = true)
    public void editPost(PostEditCommand postEditCommand) {
        // find post
        Post post = postMethodForConnectDB.findOnePostByPostId(postEditCommand.getPost_id())
                .orElseThrow(ResourceNotFoundException::new);
        // find category
        Category category = categoryMethod.findCategoryByTitle(postEditCommand.getCategoryTitle())
                .orElseThrow(ResourceNotFoundException::new);
        // delete and add tags
        tagMethod.deleteAllTag(post);
        tagMethod.createTagAndTagList(postEditCommand.getTag(), post);
        // edit
        post.postEdit(postEditCommand.getPost_id(), postEditCommand.getPostContent(), postEditCommand.getPostTitle(), category, postEditCommand.getThumbnailUrl());
        postMethodForConnectDB.postEdit(post.getPostId(), post.getPostTitle(), post.getPostContent(), post.getCategory(), post.getThumbnailUrl());
    }

    @Override
    public void deletePost(Long postId) {
        postMethodForConnectDB.delete(postId);
    }

    @Override
    public void addHit(Long postId) {
        Post post = postMethodForConnectDB.findOnePostByPostId(postId).orElseThrow(ResourceNotFoundException::new);
        post.addHit();
    }

    @Override
    public Post getPost(Long postId) {
        return postMethodForConnectDB.findOnePostByPostId(postId).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Post> getTotalPosts() {
        return postMethodForConnectDB.findAllByOrderByPostIdDesc();
    }

    @Override
    @Cacheable(value = "layoutCaching", key = "1")
    public List<PostDtoForBox> getPopularPosts() {
        return postMethodForConnectDB.findTop5ByOrderByPostHitsDesc()
                .stream()
                .map(postDtoMapper::cardBox)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDtoForBox> getRecentPosts(Long lastPostId) {
        return postMethodForConnectDB.findPostsByPostID(lastPostId, size).stream()
                .map(postDtoMapper::cardBox).collect(Collectors.toList());
    }

    @Override
    public List<PostDtoForBox> getPostByCategory(String categoryTitle) { // cur page number
        List<Post> postList = postMethodForConnectDB.findByCategoryTitle(categoryTitle);
        if (postList == null) throw new ResourceNotFoundException();
        return postList.stream().map(postDtoMapper::cardBox).collect(Collectors.toList());
    }

    //post 수정을 위한 반환
    @Override
    public PostDtoForEdit getPostDtoForEdit(Long postId) {
        Post post = postMethodForConnectDB.findPostByPostIdFetchCategoryAndTag(postId);
        PostDtoForEdit postDto = postDtoMapper.edit(post);

        List<String> postTagStringList = post.getPostTagList()
                .stream()
                .map(tagList -> tagList.getTag().getTagName())
                .collect(Collectors.toList());

        postDto.setPostTagList(postTagStringList);
        return postDto;
    }

    @Override
    public PostDtoForAllContent getPostDtoForAllContent(Long postId) {
        Post post = postMethodForConnectDB.findPostByPostIdFetchCategoryAndTag(postId);
        PostDtoForAllContent postDtoForAllContent = postDtoMapper.allContent(post);

        List<String> postTagStringList = post.getPostTagList()
                .stream()
                .map(tagList -> tagList.getTag().getTagName())
                .collect(Collectors.toList());

        postDtoForAllContent.setPostTagList(postTagStringList);
        return postDtoForAllContent;
    }

    @Override
    public Page<PostDtoForBox> getPostDtoByTag(String tag, Integer page) {
        return postMethodForConnectDB.findAllByPostTagListOrderByPostId(PageRequest.of(pageResolve(page), size), tag)
                .map(postDtoMapper::cardBox);
    }

    @Override
    public Page<PostDtoForBox> getPostDtoByKeyword(String keyword, Integer page) {
        return postMethodForConnectDB.findAllByKeywordOrderByPostId(PageRequest.of(pageResolve(page), size), keyword)
                .map(postDtoMapper::cardBox);
    }

    @Override
    public List<PostDtoByCategory> getPostDtoByCategory(String categoryTitle) {
        Category category = categoryMethod.findCategoryByTitle(categoryTitle).orElseThrow(ResourceNotFoundException::new);

        return postMethodForConnectDB.findTop5ByCategoryOrderByPostIdDesc(category)
                .stream().map(postDtoMapper::category).collect(Collectors.toList());
    }

    private int pageResolve(Integer page) {
        if (page == 1 || page == null) return 0;
        return page - 1;
    }

}
