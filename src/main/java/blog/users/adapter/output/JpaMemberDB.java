package blog.users.adapter.output;

import blog.users.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberDB extends JpaRepository<Member, Long> {
    Optional<Member> findById (Long memberId);
    Optional<Member> findByEmail (String email);
    Optional<Member> findByUserName(String username);
}
