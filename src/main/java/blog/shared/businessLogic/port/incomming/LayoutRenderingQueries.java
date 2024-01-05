package blog.shared.businessLogic.port.incomming;

import blog.category.application.port.input.CategoryMethod;
import blog.comment.application.port.input.CommentMethod;
import blog.post.application.connetionPart.input.PostMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LayoutRenderingQueries implements LayoutRenderingUseCase {
    private final CategoryMethod categoryMethod;
    private final CommentMethod commentMethod;
    private final PostMethod postMethod;
    @Override
    public void AddLayoutTo(Model model) {
        var categoryViewForLayout = categoryMethod.getCategoryViewForLayout();
        int postTotalCount = postMethod.getTotalPosts().size();
        var commentListForLayOut = commentMethod.recentCommentListForLayout();

        model.addAttribute("postTotalCount", postTotalCount);
        model.addAttribute("category", categoryViewForLayout);
        model.addAttribute("commentList", commentListForLayOut);
    }
}
