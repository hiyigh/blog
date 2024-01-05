package blog.search.application.run;

import blog.category.domain.Category;
import blog.category.application.port.input.CategoryMethod;
import blog.post.domain.Post;
import blog.post.application.connetionPart.input.PostMethod;
import blog.search.domain.SiteMap;
import blog.search.domain.XMLOutputterHelper;
import blog.search.application.port.SiteMapMethod;
import lombok.RequiredArgsConstructor;
import org.jdom2.output.XMLOutputter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteMapService implements SiteMapMethod{
    private final PostMethod postMethod;
    private final CategoryMethod categoryMethod;

    @Override
    @Cacheable(value = "seoCaching", key = "1")
    public String getSiteMap() {
        List<Post> postList = postMethod.getTotalPosts();
        List<Category> categoryList = categoryMethod.getAllCategories();
        SiteMap siteMap = SiteMap.createSiteMap(postList, categoryList);

        XMLOutputter xmlOutputter = XMLOutputterHelper.getXML();

        return xmlOutputter.outputString(siteMap.getSiteMapDoc());
    }
}
