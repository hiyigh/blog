package blog.users.inApp.port.input.response;

import blog.users.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class MemberDto implements Serializable {
    private Long memberId;
    private String userId;
    private String username;
    private String password;
    private String email;
    private String picUrl;

    public MemberDto() {}
    public static MemberDto changeMemberDto (Member member) {
        return new MemberDto(member.getMemberId(),member.getUserId(),
                member.getUserName(), member.getEmail(), member.getPicUrl(),member.getPassword());
    }
    public void makeMemberId(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedTime = currentTime.format(formatter);
        this.memberId = Long.parseLong(formattedTime);
    }
}
