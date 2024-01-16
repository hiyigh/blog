package blog.chatgpt;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatgptService chatgptService;
    private static RestTemplate restTemplate = new RestTemplate();
    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatConfig.MEDIA_TYPE));
        headers.add(ChatConfig.AUTHORIZATION, ChatConfig.BEARER + ChatConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }
    public ChatGptResponseDto getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDto> responseEntity = restTemplate.postForEntity(
                ChatConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDto.class);

        return responseEntity.getBody();
    }

    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDto(
                                ChatConfig.MODEL,
                                requestDto.getQuestion(),
                                ChatConfig.MAX_TOKEN,
                                ChatConfig.TEMPERATURE,
                                ChatConfig.TOP_P
                        )
                )
        );
    }

    public String getChatResponse(String prompt) {
        return chatgptService.sendMessage(prompt);
    }
}
