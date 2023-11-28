package blog.users.inApp.port.output;


import blog.users.domain.Member;

import java.util.Optional;

public interface MemberMethodForDB {
    void save (Member member);
    Optional<Member> findById (Long memberId);
    Optional<Member> findByEmail (String email);
    Optional<Member> findByUserName(String username);
    Optional<Member> findByProviderId (String providerId);
}
