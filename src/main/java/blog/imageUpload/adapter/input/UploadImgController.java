package blog.imageUpload.adapter.input;

import blog.imageUpload.domain.ImgFile;
import blog.imageUpload.application.port.output.ImgUploadMethodForAWS;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController // controller + request body -> controller data
@RequiredArgsConstructor
public class UploadImgController {
    private final ImgUploadMethodForAWS imgUploadMethodForAWS;
    @GetMapping("/post/uploadImg")
    public String uploadImg(@ModelAttribute ImgFile imgFile) {
        return imgUploadMethodForAWS.uploadFile(imgFile);
    }
}
