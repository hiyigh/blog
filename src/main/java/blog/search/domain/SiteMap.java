package blog.search.domain;

import blog.category.domain.Category;
import blog.post.domain.Post;
import lombok.Getter;
import org.jdom2.Document;
import org.jdom2.Element;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
@Getter
public class SiteMap {
    private static final String NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9";
    private static final String ROOT = "https://www.717276.co.kr";
    private static final String CATEGORYPRE = "/post/list?";
    private static final String CATEGORYPRO = "/&page=1";
    private static final String POSTPRE = "/post/view?postId=";
    private final Document siteMapDoc; // xml

    private SiteMap(Document siteMapDoc) {
        this.siteMapDoc = siteMapDoc;
    }

    public static SiteMap createSiteMap(List<Post> postList, List<Category> categoryList) {
        Document doc = new Document();
        Element sitemap = new Element("urlset", NAMESPACE);
        doc.setRootElement(sitemap);

        Element main = createMainElement();
        sitemap.addContent(main);

        addPostUrlToSiteMap(postList, sitemap);
        addCategoryUrlsToSiteMap(categoryList, sitemap);

        return new SiteMap(doc);
    }

    private static Element createMainElement() {
        var main = new Element("url", NAMESPACE);
        main.addContent(new Element("loc", NAMESPACE).setText(ROOT));
        main.addContent(new Element("lastmod", NAMESPACE).setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH))));
        main.addContent(new Element("priority", NAMESPACE).setText("1.0"));
        return main;
    }
    private static void addPostUrlToSiteMap(List<Post> postList, Element siteMap) {
        for (Post post : postList) {
            Element postUrl = new Element("url",NAMESPACE);
            postUrl.addContent(new Element("loc",NAMESPACE).setText(ROOT + POSTPRE + post.getPostId()));
            siteMap.addContent(postUrl);
        }
    }
    private static void addCategoryUrlsToSiteMap(List<Category> allcategoryList, Element siteMap) {
        for (Category category : allcategoryList) {
            var categoryUrl = new Element("url",NAMESPACE);
            categoryUrl.addContent(new Element("loc",NAMESPACE)
                    .setText(ROOT + CATEGORYPRE + "category="+category.getCategoryTitle()+"&tier="+category.getCategoryTitle()+CATEGORYPRO));
            siteMap.addContent(categoryUrl);
        }
    }
}
