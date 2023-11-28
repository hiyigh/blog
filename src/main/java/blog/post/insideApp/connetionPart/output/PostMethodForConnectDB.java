package blog.post.insideApp.connetionPart.output;

import blog.category.domain.Category;
import blog.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostMethodForConnectDB {
    void save(Post post);
    void delete(Long postId);
    Optional<Post> findOnePostByPostId(Long postId);
    List<Post> findAllByOrderByPostIdDesc();
    List<Post> findTop5ByOrderByPostHitsDesc();
    List<Post> findTop5ByCategoryOrderByPostIdDesc(Category category);
    List<Post> findPostsBy(int page, int size);
    List<Post> findPostsByPostID(Long articleId, int size);
    Page<Post> findAllByPostTagListOrderByPostId (Pageable pageable, String tag);
    Page<Post> findAllByKeywordOrderByPostId (Pageable pageable, String keyword);
    Post findPostByPostIdFetchCategoryAndTag(Long postId);
    List<Post> findByCategoryTitle(String categoryTitle);

    void postEdit(Long postId, String postTitle, String postContent, Category category, String thumbnailUrl);
}
