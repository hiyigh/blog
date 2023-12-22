package blog.users.adapter.output;

import blog.users.domain.Member;
import blog.users.inApp.port.output.MemberMethodForDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberAdapter implements MemberMethodForDB
{
    private final JpaMemberDB jpaMemberDB;

    @Override
    public void save(Member member) { jpaMemberDB.save(member);}

    public Optional<Member> findById (Long memberId) {
        return jpaMemberDB.findById(memberId);
    }
    public Optional<Member> findByEmail (String email) {
        return jpaMemberDB.findByEmail(email);
    }
    public Optional<Member> findByUserName(String username) {
        return jpaMemberDB.findByUserName(username);
    }
    public Optional<Member> findByProviderId(String providerId) {
        return jpaMemberDB.findByProviderId(providerId);
    }
}
