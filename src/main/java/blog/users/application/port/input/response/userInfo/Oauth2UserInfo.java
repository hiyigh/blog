package blog.users.application.port.input.response.userInfo;

import blog.users.domain.Member;
import blog.users.domain.Role;

import java.util.Map;

public interface Oauth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getUserName();
    String getPicture();
    Map<String, Object> getAttributes();
    default Member toEntity() {
        Member temp = Member.builder()
                .userName(this.getUserName())
                .email(this.getEmail())
                .userId(this.getProviderId())
                .providerId(this.getProviderId())
                .provider(this.getProvider())
                .picUrl(this.getPicture())
                .role(Role.USER)
                .build();
        return temp;
    }

}
