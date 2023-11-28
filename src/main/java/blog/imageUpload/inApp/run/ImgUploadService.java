package blog.imageUpload.inApp.run;

import blog.imageUpload.domain.ImgFile;
import blog.imageUpload.inApp.port.input.ImgUploadMethod;
import blog.imageUpload.inApp.port.output.ImgUploadMethodForAWS;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@RequiredArgsConstructor
public class ImgUploadService implements ImgUploadMethod {
    private final ImgUploadMethodForAWS imgUploadMethodForAWS;
    @Override
    public String uploadFile(MultipartFile multipartFile) {
        checkValidateFile(multipartFile);
        ImgFile imgFile = ImgFile.multipartFileToImgFile(multipartFile);
        return imgUploadMethodForAWS.uploadFile(imgFile);
    }
    private void checkValidateFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new IllegalArgumentException("no image");
    }
}
