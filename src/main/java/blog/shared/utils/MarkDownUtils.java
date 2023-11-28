package blog.shared.utils;

import lombok.Getter;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;

public class MarkDownUtils {
    private static final HtmlRenderer htmlRenderer;
    private static final Parser parser;
    static{
        htmlRenderer = HtmlRenderer.builder()
                .extensions(List.of(TablesExtension.create()))
                .build();
        parser = Parser.builder()
                .extensions(List.of(TablesExtension.create()))
                .build();
    }
    public static HtmlRenderer getHtmlRenderer() {
        return htmlRenderer;
    }

    public static Parser getParser() {
        return parser;
    }
}
