package blog.post.insideApp.connetionPart.input;
import blog.post.domain.TempPost;
import blog.post.insideApp.connetionPart.input.DtoForResponse.TempPostDto;

public interface TempPostMethod {
    void saveTempPost (String tempPostContent);
    TempPostDto getTempPost();
    void deleteTemp();
}
