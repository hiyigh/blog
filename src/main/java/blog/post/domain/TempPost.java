package blog.post.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class TempPost {
    @Id
    @Column(name = "temp_id")
    private Long id;

    @Column(nullable = false, length = 10000)
    private String content;

    public TempPost() {} // 나중에 tempService 에서 사용해야 하기 때문에 public 처리
    public TempPost(String content) {
        this.content = content;
        this.id = 1L;
    }
}
