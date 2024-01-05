package blog.search.application.run;

import blog.post.domain.Post;
import blog.post.application.connetionPart.input.PostMethod;
import blog.search.domain.RssFeed;
import blog.search.domain.XMLOutputterHelper;
import blog.search.application.port.RssMethod;
import lombok.RequiredArgsConstructor;
import org.jdom2.output.XMLOutputter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RssService implements RssMethod {
    private final PostMethod postMethod;
    @Override
    @Cacheable(value = "seoCaching", key = "0")
    public String getRssFeed() {
        List<Post> postList = postMethod.getTotalPosts();
        RssFeed rssFeed = RssFeed.createRoot(postList);
        XMLOutputter xmlOutputter = XMLOutputterHelper.getXML();

        return xmlOutputter.outputString(rssFeed.getRssDoc());
    }
}
