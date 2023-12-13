package blog.post.outsideApp.output;

import blog.post.domain.Post;
import blog.post.domain.PostTagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaPostTagListDB extends JpaRepository<PostTagList, Long> {

    @Modifying
    @Query("delete from PostTagList t " + "where t.post =:post")
    void deleteByPost(Post post);
}
