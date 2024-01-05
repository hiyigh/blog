package blog.post.adapter.output;

import blog.category.domain.Category;
import blog.post.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface JpaPostDB extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long PostId);
    List<Post> findTop5ByOrderByPostHitsDesc();
    List<Post> findTop5ByCategoryOrderByPostIdDesc(Category category);
    Slice<Post> findPostsBy(Pageable pageable);

    //카테고리별(상위 카테고리) 페이징 처리해서 최신게시물순으로 Slice 가져오기
    @Query("select a from Post a " + "inner join a.category c " + "left join c.parents p where p.categoryTitle = :category " +
            "order by a.postId desc ")
    Slice<Post> findByCategoryOrderByPostIdDesc(Pageable pageable, @Param("category") String category);

    //카테고리와 태그를 모두 페치조인해서 포스트 조회, 포스트 세부 조회용도
    @Query("select a from Post a " + "join fetch a.category " + "join fetch a.postTagList tl " + "join fetch tl.tag " +
            "where a.postId =:postId ")
    Post findPostByPostIdFetchCategoryAndTag(Long postId);

    //태그별 포스트 페이징 처리 조회
    @Query("select a " +"from Post a " + "join a.postTagList at " + "join at.tag t " + "where t.tagName =:tagName " +
            "order by a.postId desc ")
    Page<Post> findAllByPostTagListOrderByPostId(Pageable pageable, @Param("tagName") String tagName);

    //키워드별 포스트 페이징 처리해서 조회, 토탈 카운트
    @Query("select a " + "from Post a " + "where a.postTitle like %:keyword% " + "or a.postContent like %:keyword% " +
            "order by a.postId desc ")
    Page<Post> findAllByKeywordOrderByPostId(Pageable pageable, @Param("keyword") String keyword);
    List<Post> findAllByOrderByPostIdDesc();

    @Query("delete from Post a where a.postId = :postId ")
    void deleteByPostId(Long postId);
    @Query("select a from Post a " + "inner join a.category c " + "where c.categoryTitle = :categoryTitle")
    List<Post> findByCategoryTitle(String categoryTitle);

    @Modifying
    @Query("update Post p set " +
            "p.postTitle = :postTitle, p.postContent = :postContent, p.category = :category, p.thumbnailUrl = :thumbnailUrl " +
            "where p.postId = :postId")
    void postEdit(@Param("postId") Long postId,@Param("postTitle") String postTitle,@Param("postContent") String postContent,
                  @Param("category") Category category,@Param("thumbnailUrl") String thumbnailUrl);
}
