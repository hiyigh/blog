package blog.users.application.run;

import blog.users.domain.Member;
import blog.users.application.port.input.MemberMethod;
import blog.users.application.port.output.MemberMethodForDB;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberMethod {
    private final MemberMethodForDB memberMethodForDB;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Optional<Member> findById(Long memberId) {
        return memberMethodForDB.findById(memberId);
    }
    public void encodingPasswordAndSave(Member member) {
        // 사용자의 비밀번호를 BCrypt로 인코딩하여 저장
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        // 사용자 저장 로직
        memberMethodForDB.save(member);
    }
}
