package blog.imageUpload.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
@AllArgsConstructor
@Getter
public class ImgFile {
    private String originalFileName;
    private String storedFileName;
    private MultipartFile multipartFile; // file data

    public static ImgFile multipartFileToImgFile(MultipartFile multipartFile) {
        return new ImgFile(multipartFile.getOriginalFilename(),
                createStoredFileName(multipartFile.getOriginalFilename()),
                multipartFile);
    }
    private static String createStoredFileName(String originalFileName) {
        String extract = extractExt(originalFileName);
        var uuid = UUID.randomUUID().toString();
        return uuid + "." + extract;
    }
    private static String extractExt(String originalFilename) {
        var pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
