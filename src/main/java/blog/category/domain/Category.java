package blog.category.domain;

import blog.post.domain.Post;
import blog.shared.domain.BasicEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category extends BasicEntity {
    @Id
    @Column(name="category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name= "category_title", nullable = false, length = 30, unique = true)
    private String categoryTitle;

    @OneToMany(mappedBy = "category") // post 내부 category
    private List<Post> postList = new ArrayList<>();

    private int cOrder; // sidebar 순서 정렬 및 조정

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parents_id")
    private Category parents;

    @OneToMany(mappedBy = "parents")
    private List<Category> child = new ArrayList<>();

    @Override
    public String toString() {
        return categoryTitle;
    }

    public Category() {}

    @Builder
    public Category(String title, Category parents, int cOrder,List<Category> child) {
        this.categoryTitle = title;
        this.parents = parents;
        this.cOrder = cOrder;
        this.child = child;
    }
    public Long getId() {
        return categoryId;
    }
}
