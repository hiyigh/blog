package blog.imageUpload.inApp.port.output;

import blog.imageUpload.domain.ImgFile;

public interface ImgUploadMethodForAWS {
    String uploadFile(ImgFile imageFile);
}
