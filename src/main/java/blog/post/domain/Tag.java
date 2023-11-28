package blog.post.domain;

import blog.shared.domain.BasicEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Tag extends BasicEntity {
    @Id
    @Column(name="tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<PostTagList> postTagListList = new ArrayList<>();

    public Tag(String tagName) {
        this.tagName = tagName;
    }
    protected Tag() {}
}
