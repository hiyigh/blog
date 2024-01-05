package blog.post.adapter.output;

import blog.post.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CreateQuery {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Post> findByOrderByIdDesc(Long postId, int size) {
        String sql = "SELECT * FROM post WHERE post_id = :postId ORDER BY post_id DESC LIMIT :size";
        return entityManager.createNativeQuery(sql, Post.class).setParameter("postId", postId).setParameter("size", size)
                .getResultList();
    }
}
