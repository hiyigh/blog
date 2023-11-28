package blog.comment.insideApp.implMethod;

import blog.comment.domain.Comment;
import blog.comment.insideApp.port.input.CommentMethod;
import blog.comment.insideApp.port.input.Dto.CommentDto;
import blog.comment.insideApp.port.input.Dto.CommentDtoForLayout;
import blog.comment.insideApp.port.output.CommentMethodForDB;
import blog.post.domain.Post;
import blog.post.insideApp.connetionPart.input.PostMethod;
import blog.shared.exception.ResourceNotFoundException;
import blog.users.domain.Member;
import blog.users.inApp.port.input.MemberMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentMethod {
    private final CommentMethodForDB commentMethodForDB;
    private final PostMethod postMethod;
    private final MemberMethod memberMethod;

    @Override
    public void savePComment(String content, boolean secret, Long memberId, Long postId) {
         var member = memberMethod.findById(memberId).orElseThrow();
         var post = postMethod.getPost(postId);

         Comment comment = Comment.builder().post(post).comment(content)
                 .tier(0).pOrder(commentMethodForDB.countCommentsByPostAndTier(post,0))
                 .member(member).secret(secret).build();
         commentMethodForDB.saveComment(comment);
    }

    @Override
    public void saveCComment(String content, boolean secret, Long memberId, Long postId, Long parentId) {
        Member member = memberMethod.findById(memberId).orElseThrow();
        Post post = postMethod.getPost(postId);

        Comment pComment = commentMethodForDB.findById(parentId).orElseThrow(ResourceNotFoundException::new);
        Comment comment = Comment.builder().post(post).member(member).secret(secret).comment(content)
                .tier(1).pOrder(pComment.getPOrder()).parents(pComment).build();

        commentMethodForDB.saveComment(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentMethodForDB.deleteComment(commentId);
    }

    @Override
    public List<CommentDto> getCommentList(Long postId) {
        return CommentDto.recursiveCommentDtoTree(commentMethodForDB.findCommentsByPostId(postId), 0);
    }

    @Override
    public List<CommentDtoForLayout> recentCommentListForLayout() {
        return commentMethodForDB.findTop5ByOrderByCommentIdDesc().stream()
                .map(comment -> new CommentDtoForLayout(comment.getCommentId(), comment.getPost().getPostId(), comment.getComment(), comment.isSecret()))
                .collect(Collectors.toList());
    }
}
