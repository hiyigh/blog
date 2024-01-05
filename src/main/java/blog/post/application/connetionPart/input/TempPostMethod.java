package blog.post.application.connetionPart.input;
import blog.post.application.connetionPart.input.DtoForResponse.TempPostDto;

public interface TempPostMethod {
    void saveTempPost (String tempPostContent);
    TempPostDto getTempPost();
    void deleteTemp();
}
