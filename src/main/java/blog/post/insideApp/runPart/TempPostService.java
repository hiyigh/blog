package blog.post.insideApp.runPart;

import blog.post.domain.Post;
import blog.post.domain.TempPost;
import blog.post.insideApp.connetionPart.input.DtoForResponse.TagDto;
import blog.post.insideApp.connetionPart.input.DtoForResponse.TempPostDto;
import blog.post.insideApp.connetionPart.input.TagMethod;
import blog.post.insideApp.connetionPart.input.TempPostMethod;
import blog.post.insideApp.connetionPart.output.TempPostMethodForConnectDB;
import blog.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TempPostService implements TempPostMethod {
    private final TempPostMethodForConnectDB tempPostMethodForConnectDB;
    @Override
    public void saveTempPost(String tempPostContent) {
        TempPost tPost = new TempPost(tempPostContent);
        tempPostMethodForConnectDB.save(tPost);
    }

    @Override
    public TempPostDto getTempPost() {
        Optional<TempPost> tp = tempPostMethodForConnectDB.findById(1l);
        TempPostDto tempPostDto = new TempPostDto();
        tempPostDto.setContent(tp.orElse(new TempPost()).getContent());
        return tempPostDto;
    }

    @Override
    public void deleteTemp() {
        Optional<TempPost> tempPostOptional = tempPostMethodForConnectDB.findById(1l);
        if (tempPostOptional.isEmpty()) return;
        TempPost tempPost = tempPostOptional.get();
        tempPostMethodForConnectDB.delete( tempPost);
    }
}
