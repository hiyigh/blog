package blog.imageUpload.adapter.output;

import blog.imageUpload.domain.ImgFile;
import blog.imageUpload.inApp.port.output.ImgUploadMethodForAWS;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Getter
public class ImgUploadMethodForAwsAdapter implements ImgUploadMethodForAWS {
    private final AmazonS3 amazonS3;
    private String bucket;
    @Override
    public String uploadFile(ImgFile imageFile) {
        //multipartFile 가져오기
        MultipartFile multipartFile = imageFile.getMultipartFile();
        ObjectMetadata metadata = createMetaData(multipartFile);
        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, imageFile.getStoredFileName(), inputStream, metadata));
            // bucket 에 data 가 저장된다. , 파일명, 데이터, 파일 속성
        } catch (IOException e) {
            throw new IllegalArgumentException("fail upload Image");
        }
        return amazonS3.getUrl(bucket, imageFile.getStoredFileName()).toString();
    }
    private ObjectMetadata createMetaData(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }
}
