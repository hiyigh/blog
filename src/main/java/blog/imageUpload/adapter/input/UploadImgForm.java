package blog.imageUpload.adapter.input;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadImgForm {
    private MultipartFile img;
}
