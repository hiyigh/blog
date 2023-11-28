package blog.imageUpload.inApp.port.input;

import org.springframework.web.multipart.MultipartFile;

public interface ImgUploadMethod {
    String uploadFile(MultipartFile multipartFile);
}
