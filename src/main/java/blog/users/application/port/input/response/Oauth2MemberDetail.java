package blog.users.application.port.input.response;

import blog.users.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class    Oauth2MemberDetail implements OAuth2User, Serializable{
    private final Map<String, Object> attributes;
    private final Member member;
    private final MemberDto memberDto;
    public Oauth2MemberDetail(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
        this.memberDto = MemberDto.changeMemberDto(member);
    }

    @Override
    public String getName() {
        return this.member.getUserName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().getValue()));
        return authorities;
    }

    // view
    public MemberDto getMemberDto() {
        return this.memberDto;
    }
    public Long getMemberId() {
        return this.member.getMemberId();
    }
    public String getMemberPicUrl () {
        return this.member.getPicUrl();
    }
}
