package blog.users.inApp.port.input.response.userInfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProviderType {
    GOOGLE("google"),
    NAVER("naver");
    private final String value;
}
