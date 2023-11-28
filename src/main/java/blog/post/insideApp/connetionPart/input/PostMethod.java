package blog.post.insideApp.connetionPart.input;

import blog.post.domain.Post;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoByCategory;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForAllContent;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForBox;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForEdit;
import blog.post.insideApp.connetionPart.input.request.PostCreateCommand;
import blog.post.insideApp.connetionPart.input.request.PostEditCommand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostMethod {
    Long writePost (PostCreateCommand postCreateCommand);
    void editPost (PostEditCommand postEditCommand);
    void deletePost(Long postId);
    void addHit(Long postId);
    Post getPost(Long postId);
    List<Post> getTotalPosts();
    List<PostDtoForBox> getPopularPosts();
    List<PostDtoForBox> getRecentPosts(Long lastPostId);
    List<PostDtoForBox> getPostByCategory(String categoryTitle);

    PostDtoForEdit getPostDtoForEdit (Long postId);
    PostDtoForAllContent getPostDtoForAllContent(Long postId);
    Page<PostDtoForBox> getPostDtoByTag (String tag, Integer page);
    Page<PostDtoForBox> getPostDtoByKeyword(String keyword, Integer page);
    List<PostDtoByCategory> getPostDtoByCategory(String category);
}
