package blog.users.domain;

import blog.comment.domain.Comment;
import blog.post.domain.Post;
import blog.shared.domain.BasicEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BasicEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    private String picUrl;
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    public Member() {}

    @Builder
    public Member(String userName, String userId, String email,
                  String picUrl, String provider, String providerId, Role role, String password) {
        this.userName = userName;
        this.userId = userId;
        this.email = email;
        this.picUrl = picUrl;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.password = password;
    }
    public void editUserData(String username, String password) {
        this.userName = username;
        this.password = password;
    }
}
