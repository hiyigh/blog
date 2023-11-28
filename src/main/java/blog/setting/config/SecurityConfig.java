package blog.setting.config;

import blog.shared.exception.LoginFailHandler;
import blog.users.domain.Role;
import blog.users.inApp.port.input.response.MemberDetail;
import blog.users.inApp.run.MemberDetailService;
import blog.users.inApp.run.MemberService;
import blog.users.inApp.run.Oauth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Oauth2MemberService oauth2MemberService; // user 정보를 외부에서 가져와 spring framework 에 전달
    private final MemberDetailService memberService;
    private final LoginFailHandler loginFailHandler; // login 실패시 error 호출에 필요한 변수 클래스

    @Override
    public void configure(WebSecurity web) throws Exception { // 정적 리소스 예외 설정
        web
                .ignoring()
                .antMatchers("/css/**", "/static/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .antMatchers("/post/write", "/post/edit", "/post/delete", "/edit/category", "/category/edit").hasRole(Role.ADMIN.name())
                                .antMatchers("/comment/write", "/comment/delete").authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password") // password Encoder
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true") // 실패 시에 error 파라미터를 전달

                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "remember-me")

                .and()
                .csrf() // csrf 보호, // token 저장// cookie 에 토큰 저장//
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // httpOnly 의 js 접근을 false 로 변경저장

                .and()
                .oauth2Login()
                .loginPage("/login")
                .failureHandler(loginFailHandler)
                .userInfoEndpoint()
                .userService(oauth2MemberService)
                ;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
