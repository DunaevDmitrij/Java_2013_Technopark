package Global.WebPages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 12.10.13
 * Time: 8:47
 */

/**
 *  Абстрактная веб-страница.
 *  Содержит в себе общие методы (к примеру: возврат текущего времени), и методы,
 *  которые требуется переопределить в потомках (анализ запроса).
 *  Является прародителем всех остальных классов страниц.
 */

//-------------------------------------------------------------------------------------------------
@SuppressWarnings("CanBeFinal")
public abstract class WebPage {
    // Переменная для хранения статуса текущей обработки.
    protected int Status;      // нельзя final - наследуется
    private static final String HTML_DIR = "tml";
    private static final Configuration CFG = new Configuration();

    /**
     * Конструктор без параметров. Используется для инициализации полей.
     */
    protected WebPage() {
        super();
        // Статус по умолчанию
        this.Status = HttpServletResponse.SC_OK;
    }

    /**
     * Методы анализа http запроса. Соответственно для GET и POST.
     * @param request сам запрос
     * @return возвращают вариант обработки
     */
    protected abstract int analyzeRequestGET(HttpServletRequest request);
    protected abstract int analyzeRequestPOST(HttpServletRequest request);


    /** Методы для генерации страниц по запросу соответствующего типа.
     * @param request объект запроса, для получения данных сессии
     * @return  возвращают сгенерированную страницу
     */
    public String handleGET(HttpServletRequest request) {
        int routine = this.analyzeRequestGET(request);
        return this.chooseCaseHandler(routine, RequestType.GET);
    }

    public String handlePOST(HttpServletRequest request) {
        int routine = this.analyzeRequestPOST(request);
        return this.chooseCaseHandler(routine, RequestType.POST);
    }

    /**
     * Рефлексивный вызов обработчика для соответствующего варианта routine,
     * определенного при анализе запроса. Метод наследуется.
     * @param routine путь обработки
     * @param reqType тип запроса
     * @return страница, полученная от обработчика
     */
    protected String chooseCaseHandler(int routine, RequestType reqType) {
        Method[] methods = this.getClass().getDeclaredMethods();
        String result = null;

        // Цикл по всем методам текущего класса
        for (Method curMethod : methods) {
            CaseHandler anno = curMethod.getAnnotation(CaseHandler.class);

            // Выборка нужного обработчика
            if (anno != null && anno.routine() == routine && anno.reqType() == reqType) {
                try {
                    // Вызов обработчика
                    result = (String) curMethod.invoke(this);
                } catch (Exception e) {
                    System.out.println("Error: in chooseCaseHandlerGet. " + routine + "\n" + this.getClass());
                }
                break;
            }
        }
        return result;
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
     * Генерация страницы. Наполняет шаблон даннымы и возвращает в виде строк,содержащей XML.
     * @param templateName файл шаблона
     * @param context контекст для генерации html
     * @return Шаблонизированная страница.
     */
    public static String generatePage(String templateName, Map<String, Object> context) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + templateName);
            template.process(context, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return null;
        }
        return stream.toString();
    }


    /**
     * Генерация страницы с пустым контекстом (отдача простого HTML файла).
     * @param fileName Имя шаблона.
     * @return Сгенерированная страница.
     */
    public static String generatePage(String fileName) {
        // Строка с результатом
        StringBuilder fileContent = new StringBuilder();

        // Открытие файла (try с ресурсами)
        try (FileReader file = new FileReader(HTML_DIR + File.separator + fileName)) {
            int Symbol = file.read();
            // Посимвольное чтение из файла в строку
            while (Symbol != -1) {
                fileContent.append((char) Symbol);
                Symbol = file.read();
            }
        } catch (IOException exc) {
            System.out.println("IO Error: " + exc);
        }
        return fileContent.toString();
    }

    /**
     * Метод возвращает текущий статус объекта страницы.
     * @return значение статуса (HttpServletResponse.SC_#)
     */
    public int getStatus() {
        return this.Status;
    }

    /**
     * Метод для линеаризации процесса занесения данных в Map<>.
     * @param keys массив строковых ключей
     * @param values соответствующие значения полей в контейнере (любой тип)
     * @return Новосозданный контейнер
     */
    protected  static Map<String, Object> dataToKey(String[] keys, Object ... values) {
        Map<String, Object> map = new HashMap<>();
        // Цикл по элементам массива ключей
        for (int I = 0; I < keys.length; ++I) {
            map.put(keys[I], values[I]);
        }
        return map;
    }
}
