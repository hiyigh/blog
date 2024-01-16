package blog.chatgpt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    @GetMapping("/chatgpt")
    public String Chat(@RequestParam("query") String searchTerm) {
        System.out.println("question " + searchTerm);
        if (searchTerm == null) {
            throw new IllegalArgumentException("question cannot be null");
        }
        String answer = chatService.getChatResponse(searchTerm);
        System.out.println("answer " + answer);
        return answer;
    }
}
