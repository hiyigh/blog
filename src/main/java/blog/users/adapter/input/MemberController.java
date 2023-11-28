package blog.users.adapter.input;
import blog.shared.businessLogic.port.incomming.LayoutRenderingUseCase;

import blog.users.domain.Member;
import blog.users.domain.Role;
import blog.users.inApp.port.input.response.MemberDto;
import blog.users.inApp.port.output.MemberMethodForDB;
import blog.users.inApp.run.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//- 회원 로그인 폼 조회
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final LayoutRenderingUseCase layoutRenderingUseCase;
    private final MemberMethodForDB memberMethodForDB;
    private final MemberService memberService;

    @GetMapping("/login") // loginFailHandler redirect 에서 error 매개변수를 가져오는 경우
    public String loginForm(@RequestParam(value = "error", required = false)String error, Model model) {
        if(error!=null&&error.equals("OAuth2AuthenticationException")){
            model.addAttribute("errMsg","oauth2 인증 오류");
        }
        layoutRenderingUseCase.AddLayoutTo(model);
        return "login";
    }
/*
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        System.out.println("get password");
        Optional<Member> optionalMember = memberMethodForDB.findByEmail(email);
        System.out.println("confirm optional member");
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getPassword().equals(password)) {

                return "index";
            }
        }
        return "redirect:/login";
    }
*/
    @GetMapping("/join")
    public String getJoin (){
        return "join";
    }

    @PostMapping("/join")
    public String join(@RequestParam String email, @RequestParam String password, @RequestParam String username, @RequestParam String userId) {
        Optional<Member> alreadyJoinOptional = memberMethodForDB.findByEmail(email);
        if (alreadyJoinOptional.isPresent()) {
            Member alreadyJoin = alreadyJoinOptional.get();
            if (alreadyJoin.getEmail().equals(email)) {
                throw new RuntimeException("Duplicated email");
            }
        }
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(email);
        memberDto.setUsername(username);
        memberDto.setPassword(password);
        memberDto.setUserId(userId);
        memberDto.makeMemberId();

        Member newMember = Member.builder().email(memberDto.getEmail()).userId(memberDto.getUserId()).userName(memberDto.getUsername())
                .password(memberDto.getPassword()).build();
        newMember.setRole(Role.USER);
        memberService.encodingPasswordAndSave(newMember);
        return "/index";
    }

}
