package blog.search.domain;

import blog.post.domain.Post;
import lombok.Getter;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static blog.shared.utils.MarkDownUtils.getHtmlRenderer;
import static blog.shared.utils.MarkDownUtils.getParser;
@Getter
public class RssFeed {
    private static final String ROOT = "https://www.717276.co.kr/post/view?postId=";
    private final Document rssDoc;
    private RssFeed(Document rssDoc) {this.rssDoc=rssDoc;}
    public static RssFeed createRoot(List<Post> postList) {
        Document doc = new Document();
        Element rssElement = buildRssElement(); // root element
        doc.setRootElement(rssElement);

        Element channelElement = buildChannelElement(); // child element
        rssElement.addContent(channelElement);

        addPostToChannel(postList, channelElement, new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH));

        return new RssFeed(doc);
    }

    private static void addPostToChannel(List<Post> postList, Element channelElement, SimpleDateFormat simpleDateFormat) {
        for (Post post : postList) {
            Element item = createItem(post, simpleDateFormat);
            channelElement.addContent(item);
        }
    }

    private static Element createItem(Post post, SimpleDateFormat simpleDateFormat) {
        Element item = new Element("item");
        item.addContent(new Element("title").addContent(new CDATA(post.getPostTitle())))
                .addContent(new Element("link").setText(ROOT + post.getPostId()))
                .addContent(new Element("description").addContent(new CDATA(getHtmlRenderer().render(getParser().parse(post.getPostContent())))))
                .addContent(new Element("pubDate").setText(simpleDateFormat.format(Timestamp.valueOf(post.getCreatedDate()))))
                .addContent(new Element("guild").setText(ROOT + post.getPostId()));
        return item;
    }

    private static Element buildChannelElement() {
        Element channel = new Element("channel");
        channel.addContent(new Element("title").addContent(new CDATA("717276 LOG")));
        channel.addContent(new Element("link").setText("https://www.717276.co.kr"));
        channel.addContent(new Element("description").addContent(new CDATA("")));
        return channel;
    }

    private static Element buildRssElement() {
        Element rssElement = new Element("rss");
        rssElement.setAttribute(new Attribute("version", "2.0"));
        return rssElement;
    }

}
