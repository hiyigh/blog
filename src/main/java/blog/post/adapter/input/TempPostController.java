package blog.post.adapter.input;

import blog.post.application.connetionPart.input.DtoForResponse.TempPostDto;
import blog.post.application.connetionPart.input.TempPostMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TempPostController {
    private final TempPostMethod tempPostMethod;
    @PostMapping("/post/temp/autoSave")
    public String autoSave(@RequestBody TempPostDto tempPostDto) {
        tempPostMethod.saveTempPost(tempPostDto.getContent());
        return "save success";
    }
    @GetMapping("/post/temp/getTemp")
    public @ResponseBody TempPostDto getTempPost() {
        return tempPostMethod.getTempPost();
    }
}
