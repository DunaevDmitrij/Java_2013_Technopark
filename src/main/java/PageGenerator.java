import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 26.09.13
 */
public class PageGenerator {

    private static final String HTML_DIR = "tml";
    private static final Configuration CFG = new Configuration();

    /**
     * Наполняет шаблон даннымы и возвращает в виде строки.
     * @param filename Имя шаблона.
     * @param data Множество переменная-значение для шаблона.
     * @return Шаблонизированная страница.
     */
    public static String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

}
