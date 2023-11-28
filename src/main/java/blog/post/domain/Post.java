package blog.post.domain;

import blog.category.domain.Category;
import blog.shared.domain.BasicEntity;
import blog.shared.exception.BadRequestException;
import blog.users.domain.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // jpa entity 로 지정, 데이터베이스 테이블과 mapping 되는 객체이다.
@Getter
public class Post extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false, length = 50)
    private String postTitle;

    @Column(nullable = false, length = 12000)
    private String postContent;

    @Column(columnDefinition = "bigint default 0", nullable = false)
    private Long postHits;

    @Column(nullable = false)
    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private List<PostTagList> postTagList = new ArrayList<>();

    protected Post() {}
    @Builder
    public Post(String title, String content, Member member, Category category, String thumbnailUrl) {
        if (StringUtils.isEmpty(title)) throw new BadRequestException("PostForDB.title");
        if (StringUtils.isEmpty(content)) throw new BadRequestException("PostForDB.content");
        if(member == null) throw new BadRequestException("PostForDB.member");
        if(category == null) throw new BadRequestException("PostForDB.category");

        this.postTitle = title;
        this.postContent = content;
        this.member = member;
        this.thumbnailUrl = makeDefaultThumbnail(thumbnailUrl);
        this.category = category;
        this.postHits = 0L;
    }
    private String makeDefaultThumbnail(String thumbnailUrl) {
        var defaultThumbUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTdkEhYqLg3syz13oqW1p5ubsca5j3ddw87tX_2aV2VQ&s";
        if (thumbnailUrl == null || thumbnailUrl == "") {
            thumbnailUrl = defaultThumbUrl;
        }
        return thumbnailUrl;
    }
    // post 수정 로직
    public void postEdit(Long postId, String content, String title, Category category, String thumbnailUrl) {
        this.postId = postId;
        this.postTitle = title;
        this.postContent = content;
        this.category = category;
        if (thumbnailUrl != null) {
            this.thumbnailUrl = getThumbnailUrl();
        }
    }
    public void addHit() {
        ++this.postHits;
    }
}
