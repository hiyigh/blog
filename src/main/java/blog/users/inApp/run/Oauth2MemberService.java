package blog.users.inApp.run;

import blog.users.domain.Member;
import blog.users.inApp.port.input.response.MemberDetail;
import blog.users.inApp.port.input.response.Oauth2MemberDetail;
import blog.users.inApp.port.input.response.userInfo.Oauth2UserInfo;
import blog.users.inApp.port.output.MemberMethodForDB;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberMethodForDB memberMethodForDB;
    private final UserInfoCreateFactory userInfoCreateFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest); // 받아온 사용자 정보
        Oauth2UserInfo userInfo = userInfoCreateFactory.makeOauth2UerInfo(userRequest.getClientRegistration().getRegistrationId(), oauth2User);
        Member member = makeMember(userInfo);
        return new Oauth2MemberDetail(member, userInfo.getAttributes());
    }

    private Member makeMember(Oauth2UserInfo oauth2UserInfo) {
        return memberMethodForDB.findByProviderId(oauth2UserInfo.getProviderId()).orElseGet(() -> signNewMember(oauth2UserInfo));
    }

    private Member signNewMember(Oauth2UserInfo userInfo) {
        memberMethodForDB.findByEmail(userInfo.getEmail()).ifPresent(alreadyMember -> {
            throw new OAuth2AuthenticationException("duplicateEmail");
        });
        Member newMember = userInfo.toEntity();
        memberMethodForDB.save(newMember);
        return newMember;
    }

    // 다른 필요한 메서드 및 로직을 추가할 수 있습니다.
}
