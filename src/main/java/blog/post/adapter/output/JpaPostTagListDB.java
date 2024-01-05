package blog.post.adapter.output;

import blog.post.domain.Post;
import blog.post.domain.PostTagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaPostTagListDB extends JpaRepository<PostTagList, Long> {

    @Modifying
    @Query("delete from PostTagList t " + "where t.post =:post")
    void deleteByPost(Post post);
}
