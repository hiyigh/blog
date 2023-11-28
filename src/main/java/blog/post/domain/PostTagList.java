package blog.post.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "POST_TAG_LIST_SEQ_GENERATOR",
        sequenceName = "POST_TAG_LIST_SEQ",
        initialValue = 1,
        allocationSize = 50)
@Getter
public class PostTagList {
    @Id
    @Column(name="post_tag_list")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_TAG_LIST_SEQ_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public PostTagList(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
    protected PostTagList() {}
}
