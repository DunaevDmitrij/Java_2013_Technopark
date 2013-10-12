import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 12.10.13
 * Time: 8:47
 * To change this template use File | Settings | File Templates.
 */

/**
 *  Абстрактная веб-страница.
 *  Содержит в себе общие методы (к примеру: возврат текущего времени), и методы,
 *  которые требуется переопределить в потомках (генерация страниц по запросу).
 *  Является прародителем всех остальных классов страниц.
 *
 *  Содержит в себе метод для создания объектов страниц.
 */
abstract public class WebPage {
    // Переменная для хранения статуса текущей обработки.
    protected int Status;

    /** Методы для генерации страниц по соответствующему запросу.
     *  Должны анализировать сессию, создавать контекст и вызывать генерацию (generatePage()).
     * @param request объект запроса, для получения данных сессии
     * @param users  контейнер пользователей для проверки прав
     * @return  возвращает сгенерированную страницу
     */
    abstract public String handleGET(HttpServletRequest request, Map<String, Long> users);
    abstract public String handlePOST(HttpServletRequest request, Map<String, Long> users);

    /**
     * Конструктор без параметров. Используется для инициализации полей.
     */
    protected WebPage() {
        // Статус по умолчанию
        this.Status = HttpServletResponse.SC_OK;
    }

    /**
     * Создание объекта страницы в зависимости от переданного URL.
     * @param Path строка-параметр для сопоставления
     * @return объект WebPage с нужной реализацией
     */
    public static WebPage createPage(String Path) {
        if (Path.equals("/auth") || Path.equals("/test"))
            return new AuthPage();
        else
            return null;
    }

    /**
     * Метод для сбора информации о текущем моменте времени.
     * @return строка, с текущим значением времени.
     */
    protected String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss"); // здесь задаем формат времени
        return formatter.format(date);
    }

    /**
     * Генерация страницы с помощью PageGenerator
     * @param templateName файл шаблона
     * @param context контекст
     * @return сгенерированный html
     */
    protected String generatePage(String templateName, Map<String, Object> context) {
        return PageGenerator.getPage(templateName, context);
    }

    /**
     * Метод возвращает текущий статус объекта страницы.
     * @return значение статуса (HttpServletResponse.SC_#)
     */
    public int getStatus() {
        return Status;
    }

    protected  Map<String, Object> dataToKey(String[] keys, Object ... values) {
        Map<String, Object> map = new HashMap<>();
        for (int I = 0; I < keys.length; ++I) {
            map.put(keys[I], values[I]);
        }
        return map;
    }
}
