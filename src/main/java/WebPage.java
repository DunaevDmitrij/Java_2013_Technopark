import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 12.10.13
 * Time: 8:47
 */

/**
 *  Абстрактная веб-страница.
 *  Содержит в себе общие методы (к примеру: возврат текущего времени), и методы,
 *  которые требуется переопределить в потомках (генерация страниц по запросу).
 *  Является прародителем всех остальных классов страниц.
 *
 *  Содержит в себе метод для создания объектов страниц.
 */

abstract class WebPage {
    // Переменная для хранения статуса текущей обработки.
    protected int Status;      // нельзя final - наследуется
    private static final String HTML_DIR = "tml";
    private static final Configuration CFG = new Configuration();

    /** Методы для генерации страниц по соответствующему запросу.
     *  Должны анализировать сессию, создавать контекст и вызывать генерацию (generatePage()).
     * @param request объект запроса, для получения данных сессии
     * @return  возвращает сгенерированную страницу
     */
    abstract String handleGET(HttpServletRequest request);
    abstract String handlePOST(HttpServletRequest request);

    /**
     * Конструктор без параметров. Используется для инициализации полей.
     */
    protected WebPage() {
        // Статус по умолчанию
        this.Status = HttpServletResponse.SC_OK;
    }

    /**
     * Метод для сбора информации о текущем моменте времени.
     * @return строка, с текущим значением времени.
     */
    protected static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss"); // здесь задаем формат времени
        return formatter.format(date);
    }

    /**
     * Генерация страницы. Наполняет шаблон даннымы и возвращает в виде строки.
     * @param templateName файл шаблона
     * @param context контекст для генерации html
     * @return Шаблонизированная страница.
     */
    protected static String generatePage(String templateName, Map<String, Object> context) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + templateName);
            template.process(context, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }


    protected static String generatePage(String templateName) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + templateName);
            template.process(new HashMap<>(), stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }


    protected static String textPage(String text) {
        Writer stream = new StringWriter();
        return stream.toString();
    }

    /**
     * Метод возвращает текущий статус объекта страницы.
     * @return значение статуса (HttpServletResponse.SC_#)
     */
    public int getStatus() {
        return this.Status;
    }

    protected  static Map<String, Object> dataToKey(String[] keys, Object ... values) {
        Map<String, Object> map = new HashMap<>();
        for (int I = 0; I < keys.length; ++I) {
            map.put(keys[I], values[I]);
        }
        return map;
    }
}
