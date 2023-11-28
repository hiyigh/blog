package blog.post.outsideApp.output;

import blog.category.domain.Category;
import blog.post.domain.Post;
import blog.post.insideApp.connetionPart.output.PostMethodForConnectDB;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostDbAdapter implements PostMethodForConnectDB {
    private final JpaPostDB jpaPostDB;
    private final CreateQuery createQuery;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Post post) {
        jpaPostDB.save(post);
    }

    @Override
    public void delete(Long postId) {
        jpaPostDB.deleteById(postId);
    }

    @Override
    public Optional<Post> findOnePostByPostId(Long postId) {
        return jpaPostDB.findById(postId);
    }

    @Override
    public List<Post> findAllByOrderByPostIdDesc() {
        return jpaPostDB.findAllByOrderByPostIdDesc();
    }

    @Override
    public List<Post> findTop5ByOrderByPostHitsDesc() {
        return jpaPostDB.findTop5ByOrderByPostHitsDesc();
    }

    @Override
    public List<Post> findTop5ByCategoryOrderByPostIdDesc(Category category) {
        return jpaPostDB.findTop5ByCategoryOrderByPostIdDesc(category);
    }

    @Override
    public List<Post> findPostsBy(int page, int size) {
        return jpaPostDB.findPostsBy(PageRequest.of(page,size)).getContent();
    }

    @Override
    public List<Post> findPostsByPostID(Long postId, int size) {
        return createQuery.findByOrderByIdDesc(postId, size);
    }
    @Override
    public Page<Post> findAllByPostTagListOrderByPostId(Pageable pageable, String tagName) {
        return jpaPostDB.findAllByPostTagListOrderByPostId(pageable, tagName);
    }

    @Override
    public Post findPostByPostIdFetchCategoryAndTag(Long postId) {
        return jpaPostDB.findPostByPostIdFetchCategoryAndTag(postId);
    }

    @Override
    public Page<Post> findAllByKeywordOrderByPostId(Pageable pageable, String keyword) {
        return jpaPostDB.findAllByKeywordOrderByPostId(pageable, keyword);
    }

    @Override
    public List<Post> findByCategoryTitle(String categoryTitle) {
        return jpaPostDB.findByCategoryTitle(categoryTitle);
    }

    @Override
    public void postEdit(Long postId, String postTitle, String postContent, Category category, String thumbnailUrl) {
        jpaPostDB.postEdit(postId, postTitle, postContent, category, thumbnailUrl);
    }
}
