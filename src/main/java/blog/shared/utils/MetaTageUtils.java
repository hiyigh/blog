package blog.shared.utils;

import java.util.List;

public class MetaTageUtils {
    public static String buildMetaTag(List<String> tags) {
        var metaTags = new StringBuilder();
        for (var tag : tags) {
        metaTags.append(tag).append(", ");
        }
        return metaTags.toString();
    }
}
