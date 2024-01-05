package blog.users.application.run;

import blog.users.domain.Member;
import blog.users.application.port.input.response.MemberDetail;
import blog.users.application.port.output.MemberMethodForDB;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {
    private final MemberMethodForDB memberMethodForDB;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberMethodForDB.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found with username: " + email));
        // 사용자 정보를 기반으로 CustomUserDetails 객체 생성
        return new MemberDetail(member);
    }
}
