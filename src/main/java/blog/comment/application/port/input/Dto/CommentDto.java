package blog.comment.application.port.input.Dto;

import blog.comment.domain.Comment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommentDto {
    private Long id;
    private Long parentId;
    private Long memberId;
    private int tier;
    private String username;
    private String picUrl;
    private String content;
    private boolean secret;
    private LocalDateTime createdDate;
    private List<CommentDto> childCommentList = new ArrayList<>();

    public CommentDto(Comment comment) {
        this.id = comment.getCommentId();
        this.tier = comment.getTier();
        this.parentId = Optional.ofNullable(comment.getParents()).orElseGet(() -> comment).getCommentId();
        this.content = comment.getComment();
        this.secret = comment.isSecret();
        this.createdDate = comment.getCreatedDate();

        this.username = comment.getMember().getUserName();
        this.picUrl = comment.getMember().getPicUrl();
        this.memberId = comment.getMember().getMemberId();
    }

    public static List<CommentDto> recursiveCommentDtoTree(List<Comment> source, int dept) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        if (source.isEmpty() || source == null) return commentDtoList;

        while (!source.isEmpty()) {
            Comment comment = source.get(0);
            if (comment.getTier() == dept) {
                commentDtoList.add(new CommentDto(comment));
                source.remove(0);
            } else if (comment.getTier() > dept) {
                var childComment = recursiveCommentDtoTree(source, dept + 1);
                if (childComment != null) {
                    commentDtoList.get(commentDtoList.size()-1).setChildCommentList(childComment);
                }
            } else {
                return commentDtoList;
            }
        }
        return commentDtoList;
    }
}
