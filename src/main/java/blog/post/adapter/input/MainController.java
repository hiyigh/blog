package blog.post.adapter.input;

import blog.post.application.connetionPart.input.DtoForResponse.PostDtoForBox;
import blog.post.application.connetionPart.input.PostMethod;
import blog.shared.businessLogic.port.incomming.LayoutRenderingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final PostMethod postMethod;
    private final LayoutRenderingUseCase layoutRenderingUseCase;
    //home 화면
    @GetMapping("/")
    public String main (Model model) {
        List<PostDtoForBox> postDtoForBox = postMethod.getPopularPosts();
        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("popularPosts", postDtoForBox);
        return "index";
    }
    //최신 기사 무한 스크롤 조회
    @GetMapping("/main/post/{lastPostId}")
    @ResponseBody List<PostDtoForBox> mainNextPage(@PathVariable(required = false) Long lastPostId) {
        List<PostDtoForBox> postDtoForBoxes = postMethod.getRecentPosts(lastPostId);
        for (PostDtoForBox post : postDtoForBoxes) {
            post.parseAndRenderForView(); // ResponseBody 때문에 필요한 parser method
        }
        return postDtoForBoxes;
    }
    //about me page
    @GetMapping("/aboutMe")
    public String aboutMe(Model model) {
        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("pageTitle", "신입 개발자 입니다.");
        model.addAttribute("welcome", "welcome.");
        model.addAttribute("aboutMe", "");
        return "aboutMe";
    }
}
