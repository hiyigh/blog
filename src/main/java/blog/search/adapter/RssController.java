package blog.search.adapter;

import blog.search.application.port.RssMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RssController {
    private final RssMethod rssMethod;
    @GetMapping(value = "/rss",produces = "application/xml;charset=utf-8")
    public String rssFeed() {
        return rssMethod.getRssFeed();
    }
}
