package blog.users.application.run;

import blog.shared.exception.BadRequestException;
import blog.users.application.port.input.response.userInfo.GoogleUserInfo;
import blog.users.application.port.input.response.userInfo.NaverUserInfo;
import blog.users.application.port.input.response.userInfo.Oauth2UserInfo;
import blog.users.application.port.input.response.userInfo.ProviderType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UserInfoCreateFactory {
    private static final Map<ProviderType, Function<OAuth2User, Oauth2UserInfo>> userInfoFactoryMap;
    static { // userInfoFactory 초기화
        userInfoFactoryMap = Collections.synchronizedMap(new EnumMap<>(ProviderType.class));
        userInfoFactoryMap.put(ProviderType.GOOGLE, GoogleUserInfo::new);
        userInfoFactoryMap.put(ProviderType.NAVER, NaverUserInfo::new);
    }
    private static final Map<String, ProviderType> stringToEnum;
    // String 파라미터를 열거타입으로 컨버팅하기위한 매핑
    static {
        stringToEnum = Collections.synchronizedMap(Stream.of(ProviderType.values())
                .collect(Collectors.toMap(ProviderType::getValue, providerType->providerType)));
    }
    private Optional<ProviderType> createEnumFromString(String provider) {
        return Optional.ofNullable(stringToEnum.get(provider));
    }

    public Oauth2UserInfo makeOauth2UerInfo(String registrationId, OAuth2User oAuth2User) {
        Optional<ProviderType> providerTypeOptional = createEnumFromString(registrationId); // providerType 생성
        return userInfoFactoryMap.get(providerTypeOptional.orElseThrow(BadRequestException::new)).apply(oAuth2User);
        //providerTypeOptional.orElseThrow(BadRequestException::new)를 통해 ProviderType 가져온다
        //해당 ProviderType 을 사용하여 userInfoFactoryMap 에서

        // Function<OAuth2User, Oauth2UserInfo>를 가져온다
        //가져온 함수에 oAuth2User 를 적용하여 Oauth2UserInfo 를 생성

        //userInfoFactoryMap 에서 가져온 함수는 Function<OAuth2User, Oauth2UserInfo> 타입
        //이 함수는 OAuth2User 객체를 입력으로 받아서 Oauth2UserInfo 객체를 반환하는 역할
    }
}
