package blog.comment.domain;

import blog.post.domain.Post;
import blog.shared.domain.BasicEntity;
import blog.users.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String comment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parents;

    @OneToMany(mappedBy = "parents", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> childList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int tier;
    private int pOrder; // parent Order

    @Column(columnDefinition = "boolean default false")
    private boolean secret;

    @Builder
    public Comment(Post post, String comment, Comment parents, Member member, int tier, int pOrder, boolean secret) {
        this.post = post;
        this.comment = removeDuplicatedEnter(comment);
        this.parents = parents;
        this.member = member;
        this.tier = tier;
        this.pOrder = pOrder;
        this.secret = secret;
    }
    protected Comment() {}

    private String removeDuplicatedEnter(String content) {

        if (content == null || content.isEmpty()) {
            throw new CommentBadRequestException();
        }
        char[] charBox = new char[content.length()];
        int boxIdx = 0;

        for (int i = 0; i < content.length(); ++i) {
            if (content.charAt(i) == '\n') {
                while (i < content.length() - 1 && content.charAt(i + 1) == '\n') {
                    ++i;
                }
                charBox [boxIdx++] = content.charAt(i);
            } else {
                charBox[boxIdx++] = content.charAt(i);
            }
        }

        return String.valueOf(charBox);
    }
}
