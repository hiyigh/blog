package blog.search.domain;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLOutputterHelper {
    public static XMLOutputter getXML() {
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        format.setLineSeparator("\r\n");
        return new XMLOutputter(format);
    }
}
