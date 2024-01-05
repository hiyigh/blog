package blog.comment.adapter.input;

import blog.comment.application.port.input.CommentMethod;
import blog.comment.application.port.input.Dto.CommentDto;
import blog.users.application.port.input.response.MemberDetail;
import blog.users.application.port.input.response.Oauth2MemberDetail;
import blog.users.application.port.input.response.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentMethod commentMethod; // data 조회
    private final CommentValidator commentValidator;
    @GetMapping("/comment/list/{postId}")
    public List<CommentDto> getCommentList(@PathVariable Long postId){
        List<CommentDto> test = commentMethod.getCommentList(postId);
        return test;
    }
    @PostMapping("/comment/delete")
    public List<CommentDto> deleteComment(@RequestParam Long postId, @RequestParam Long commentId) {
        commentMethod.deleteComment(commentId);
        return commentMethod.getCommentList(postId); // 최신 댓글 목록 반환
    }
    @PostMapping("/comment/write")
    public List<CommentDto> writeComment(@RequestParam Long postId,
                                         @RequestParam(required = false) Long parentId,
                                         @RequestBody CommentForm commentForm, Errors errors,
                                         // body request 를 commentForm 으로 변경
                                         @AuthenticationPrincipal Oauth2MemberDetail principal,
                                         @AuthenticationPrincipal MemberDetail memberDetail) {
        commentValidator.validate(commentForm, errors);
        // == @validated commentForm , errors
        MemberDto member = null;
        if (principal == null && memberDetail != null) {
            member = MemberDto.changeMemberDto(memberDetail.getMember());
        } else if (principal != null && memberDetail == null ) {
            member = principal.getMemberDto();
        }

        if (parentId == null) {
            commentMethod.savePComment(commentForm.getContent(), commentForm.isSecret(), member.getMemberId(), postId);
        } else {
            commentMethod.saveCComment(commentForm.getContent(), commentForm.isSecret(), member.getMemberId(), postId, parentId);
        }
        List<CommentDto> test = commentMethod.getCommentList(postId);

        return commentMethod.getCommentList(postId);
    }
}
