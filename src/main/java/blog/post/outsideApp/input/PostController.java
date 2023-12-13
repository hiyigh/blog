package blog.post.outsideApp.input;

import blog.category.insideApp.port.input.CategoryMethod;
import blog.category.insideApp.port.input.DtoForResponse.CategoryDto;
import blog.post.domain.Post;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoByCategory;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForAllContent;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForBox;
import blog.post.insideApp.connetionPart.input.DtoForResponse.PostDtoForEdit;
import blog.post.insideApp.connetionPart.input.PostMethod;
import blog.post.insideApp.connetionPart.input.TagMethod;
import blog.post.insideApp.connetionPart.input.TempPostMethod;
import blog.post.insideApp.connetionPart.input.request.PostCreateCommand;
import blog.post.insideApp.connetionPart.input.request.PostEditCommand;
import blog.post.insideApp.runPart.PostDtoMapper;
import blog.shared.businessLogic.port.incomming.LayoutRenderingUseCase;
import blog.shared.utils.MetaTageUtils;
import blog.users.domain.Member;
import blog.users.inApp.port.input.MemberMethod;
import blog.users.inApp.port.input.response.MemberDetail;
import blog.users.inApp.port.input.response.Oauth2MemberDetail;
import blog.users.inApp.port.input.response.MemberDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostMethod postMethod;
    private final TempPostMethod tempPostMethod;
    private final PostDtoMapper postDtoMapper;
    private final TagMethod tagMethod;
    private final CategoryMethod categoryMethod;
    private final MemberMethod memberMethod;
    private final LayoutRenderingUseCase layoutRenderingUseCase;

    @GetMapping("/post/write")
    public String getPostInputForm(Model model) {
        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("categoryInput", categoryMethod.findAllCategoryDto());
        model.addAttribute("tagsInput", tagMethod.findAllTagDtos());
        model.addAttribute("postDto", new PostInputForm());
        return "post/postWriteForm";
    }
    @PostMapping("/post/write")
    public String writePost(@Validated PostInputForm postInputForm, @AuthenticationPrincipal Oauth2MemberDetail oauth2MemberDetail,
                            @AuthenticationPrincipal MemberDetail memberDetail, Errors errors, Model model) {
        if(errors.hasErrors()) return "post/postWriteForm";
        PostCreateCommand postCreateCommand = null;
        if (oauth2MemberDetail == null) {
            postCreateCommand = PostCreateCommand.from(postInputForm, memberDetail.getMember().getMemberId());
        } else {
            postCreateCommand = PostCreateCommand.from(postInputForm, oauth2MemberDetail.getMemberId());
        }
        Long postId = postMethod.writePost(postCreateCommand);
        tempPostMethod.deleteTemp();
        model.addAttribute("post", postMethod.getPost(postId));
        return "redirect:/post/view?postId=" + postId;
    }
    @GetMapping("/post/edit")
    public String getEditPostForm(@RequestParam Long postId, Model model) {
        PostDtoForEdit postDtoForEdit = postMethod.getPostDtoForEdit(postId);
        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("categoryInput", categoryMethod.findAllCategoryDto());
        model.addAttribute("tagsInput", tagMethod.findAllTagDtos());
        model.addAttribute("postDto", postDtoForEdit);
        return "post/postEditForm";
    }
    @PostMapping("/post/edit")
    public String editPost(@RequestParam Long postId, @ModelAttribute PostInputForm postInputForm) {
        PostEditCommand postDtoForEdit = PostEditCommand.from(postId, postInputForm);
        postMethod.editPost(postDtoForEdit);
        return "redirect:/post/view?postId=" + postId;
    }

    @PostMapping("/post/delete")
    @Transactional
    public String deletePost(@RequestParam Long postId) {
        postMethod.deletePost(postId);
        return "redirect:/";
    }
    @GetMapping("post/list")
    public String getPostListByCategory(@RequestParam String categoryTitle,
                                        @RequestParam int page,
                                        Model model) {
        List<PostDtoForBox> postDtoForBoxList = new ArrayList<>();

        if (categoryTitle.equals("total")) {
            List<Post> postList = postMethod.getTotalPosts();
            postDtoForBoxList = postList.stream().map(postDtoMapper::cardBox).collect(Collectors.toList());
        } else {
            postDtoForBoxList = postMethod.getPostByCategory(categoryTitle);
        }

        int postCnt = getPostCntByCategoryTitle(categoryTitle);
        PagingBoxHandler pagingBoxHandler = PagingBoxHandler.create(page, postCnt);
        List<PostDtoForBox> pagingBox = pagingBoxList(pagingBoxHandler, postDtoForBoxList);
        for (PostDtoForBox box : postDtoForBoxList) {
            box.parseAndRenderForView();
        }

        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("pagingBox", pagingBoxHandler);
        model.addAttribute("postList", pagingBox);
        return "post/postList";
    }

    private List<PostDtoForBox> pagingBoxList(PagingBoxHandler pagingBoxHandler, List<PostDtoForBox> postDtoForBoxList) {
        List<PostDtoForBox> newBox = new ArrayList<>();
        for (int start = pagingBoxHandler.getListStartNum() - 1; start < postDtoForBoxList.size()
                && start < pagingBoxHandler.getListEndNum(); ++start) {
            newBox.add(postDtoForBoxList.get(start));
        }
        return newBox;
    }

    @GetMapping("post/list/tag/")
    public String getPostListByTag(@RequestParam String tag, @RequestParam Integer page, Model model) {

        Page<PostDtoForBox> postDtoForBoxList = postMethod.getPostDtoByTag(tag, page);
        for (PostDtoForBox box : postDtoForBoxList) box.parseAndRenderForView();
        PagingBoxHandler pagingBoxHandler = PagingBoxHandler.create(page, (int)postDtoForBoxList.getTotalElements());

        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("postList", postDtoForBoxList);
        model.addAttribute("pagingBox", pagingBoxHandler);
        return "post/postListByTag";
    }
    @GetMapping("post/list/search/")
    public String getPostListByKeyword(@RequestParam Integer page, @RequestParam String keyword, Model model) {
        Page<PostDtoForBox> postDtoForBoxes = postMethod.getPostDtoByKeyword(keyword, page);
        for (PostDtoForBox box : postDtoForBoxes) box.parseAndRenderForView();
        PagingBoxHandler pagingBoxHandler = PagingBoxHandler.create(page, (int)postDtoForBoxes.getTotalElements());
        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("postList", postDtoForBoxes);
        model.addAttribute("pagingBox", pagingBoxHandler);
        return "post/postListByKeyword";
    }

    private int getPostCntByCategoryTitle(String categoryTitle) {
        if (categoryTitle.equals("total")) {
            return postMethod.getTotalPosts().size();
        }

        List<Post> postList = postMethod.getTotalPosts();
        int cnt = 0;
        for (Post post : postList) {
            if (post.getCategory().getCategoryTitle().equals(categoryTitle)) ++cnt;
        }
        return cnt;
    }

    @GetMapping("/post/view")
    public String viewPost (@RequestParam Long postId, @AuthenticationPrincipal Oauth2MemberDetail Oauth2MemberDetail,
                            @AuthenticationPrincipal MemberDetail memberDetail, @CookieValue(required = false, value = "view") String cookie,
                            HttpServletResponse response, Model model) {
        if (Oauth2MemberDetail == null && memberDetail != null) {
            Optional<Member> refresh = memberMethod.findById(memberDetail.getMember().getMemberId());
            Member member = refresh.get();
            MemberDto memberDto = MemberDto.changeMemberDto(member);
            model.addAttribute("memberDto",memberDto);
        } else if (memberDetail == null && Oauth2MemberDetail != null) {
            Optional<Member> refresh = memberMethod.findById(Oauth2MemberDetail.getMemberId());
            Member member = refresh.get();
            MemberDto memberDto = MemberDto.changeMemberDto(member);
            model.addAttribute("memberDto",memberDto);
        }
        // ! member postList -> proxy error

        PostDtoForAllContent postDtoForAllContent = postMethod.getPostDtoForAllContent(postId);
        postDtoForAllContent.parseAndRenderForView(); // markdown -> html

        String category = postDtoForAllContent.getCategory().getCategoryTitle();
        List<PostDtoByCategory> postDtoByCategories = postMethod.getPostDtoByCategory(category);
        // for view
        String metaTagList = MetaTageUtils.buildMetaTag(postDtoForAllContent.getPostTagList()); // get tag list to string
        String subStringContent = getsContentToSubString(postDtoForAllContent.getPostContent());

        layoutRenderingUseCase.AddLayoutTo(model);
        model.addAttribute("post", postDtoForAllContent);
        model.addAttribute("metaTags", metaTagList);
        model.addAttribute("metaContents", Jsoup.parse(subStringContent).text());
        model.addAttribute("postListByCategory", postDtoByCategories);

        if (checkCookieForCountHit(postId, cookie, response)) postMethod.addHit(postId);
        return "post/postView";
    }

    private boolean checkCookieForCountHit(Long postId, String cookie, HttpServletResponse response) {
        if (cookie == null) {
            Cookie viewCookie = new Cookie("view", postId + "/");
            viewCookie.setComment("forHit");
            viewCookie.setMaxAge(3600);
            response.addCookie(viewCookie); // 새로운 쿠키 등록
            return true; // 조회수 add
        } else {
            boolean exist = false;
            boolean addHit = false;
            String[] postIdArray = cookie.split("/");
            for (String element : postIdArray) {
                if (element.equals(String.valueOf(postId))) { // 쿠키에 있는 동안은 추가 X
                    exist = true;
                    break;
                }
            }
            if (!exist) { // 쿠키에 없다면 addHit
                cookie += postId + "/";
                addHit = true;
            }
            response.addCookie(new Cookie("view", cookie)); // 쿠키 갱신
            return addHit;
        }
    }

    private String getsContentToSubString(String content) {
        String subString = null;
        if (content.length() > 100) {
            subString = content.substring(0,100);
        } else subString = content;
        return subString;
    }
}
