package blog.users.inApp.port.input;

import blog.users.domain.Member;
import java.util.Optional;

public interface MemberMethod {
    Optional<Member> findById (Long memberId);
}
