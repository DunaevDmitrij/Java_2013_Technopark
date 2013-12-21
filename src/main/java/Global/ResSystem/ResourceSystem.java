package Global.ResSystem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 23.11.13
 * Time: 8:44
 */

/**
 * Класс инкапсулирующий задачу ресурсной системы.
 * При старте сервера загружает все файлы ресурсов в формате xml.
 * В процессе работы прямым вызовом можно получить объект представляющий некоторую запись с данными,
 * используя уникальный ключ и имя файла.
 * Все имена файлов - константы.
 * Является ThreadSafe т. к. не изменяет внутренние данные, а только лишь читает их при вызове.
 * Является синглтоном.
 */
public class ResourceSystem {
    // Объект синглтона
    private static final ResourceSystem instance = new ResourceSystem();

    // Директория ресурсов по умолчанию
    private static final String RES_DIR = "resources";

    // Файл с системными параметрами
    private static final String PARAMS = "params.xml";
    // и полное имя класса
    private static final String PARAMS_CLASS = "Global.ResSystem.SysParam";

    // Хранилище данных (первая строка - имя файла, вторая строка - уникальный ключ)
    private Map<String, Map<String, ? extends XML_Convertable>> store;
    // Фабрика парсеров
    private final SAXParserFactory factory = SAXParserFactory.newInstance();


    protected ResourceSystem() {
        super();
        this.store = new HashMap<>();
        // Загрузка системных параметров
        this.<SysParam<?>> loadResource(PARAMS, PARAMS_CLASS);
    }


    public static ResourceSystem getRS() {
        return instance;
    }


    /**
     *  Загрузка файла ресурсов в хранилище.
     *  Внимание: второй параметр требует полное имя класса (со всеми пакетами и вмещающими классами).
     *  @param <ContentType> - тип, на который отображать записи из файла.
     *  @param xmlDir - директория с ресурсами, где лежит файл
     *  @param xmlFile - имя файла, с расширением
     *  @param className - полное имя класса (нужно для рефлексии)
      */
    protected <ContentType extends XML_Convertable>
    void loadResource(String xmlDir, String xmlFile, String className) {
        try {
            SAXParser parser = this.factory.newSAXParser();
            SAX_Handler<ContentType> handler = new SAX_Handler<>(className);

            // анализ файла с помощью созданного обработчика
            parser.parse(xmlDir + File.separator + xmlFile, handler);

            // кладем в хранилище результат работы анализатора
            this.store.put(xmlFile, handler.getData());

        } catch (Exception e) {
            System.out.println("Ошибка при загрузке файла: " + xmlFile);
            e.printStackTrace();
        }
    }

    /**
     * Вариант предыдущего метода с директорией ресурсов по умолчанию
     */
    protected <ContentType extends XML_Convertable>
    void loadResource(String xmlFile, String className) {
        this.<ContentType> loadResource(RES_DIR, xmlFile, className);
    }

    /**
     * Выборка значения системного параметра через его имя
     * @param name имя параметра
     * @param <ValueType> ожидаемый тип поля значения параметра
     * @return значение системного параметра
     */
    public <ValueType>
    ValueType getParam(String name) {
        SysParam<ValueType> param = (SysParam<ValueType>) this.store.get(PARAMS).get(name);
        return param.getValue();
    }

    /**
     * Выборка объекта записи.
     * @param xmlFile - имя файла, из которого была прочитана запись
     * @param uniqueValue - значение уникального поля(-ей)
     * @param <ContentType> - ожидаемый тип записи
     * @return
     */
    public <ContentType extends XML_Convertable>
    ContentType getRecord(String xmlFile, String uniqueValue) {
        return (ContentType) this.store.get(xmlFile).get(uniqueValue);
    }
}