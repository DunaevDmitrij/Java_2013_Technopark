package Global.ResSystem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 07.12.13
 * Time: 9:14
 */

/**
 * Обработчик xml.
 * Создает контейнер объектов ReadType и возвращает ссылку на него ресурсной системе.
 * @param <ReadType> класс, в который отображается запись из xml файла
 */
public class SAX_Handler
        <ReadType extends XML_Convertable>
        extends DefaultHandler {

    private String classFullName;             // полное имя класса (с пакетами и вмещающими классами)
    private String className;                 // только имя класса
    private Map<String, ReadType> records;    // контейнер с прочитанными данными

    private ReadType record = null;           // текущая запись
    private StringBuilder fieldValue;         // Значение текущего тега (между открывающим и закрывающим)
    private String type;                      // тип поля (для записей дженериков)

    public SAX_Handler(String classFullName) {
        super();
        this.records = new HashMap<>();
        this.classFullName = classFullName;
        this.fieldValue = new StringBuilder();

        this.className = this.getClassName();
    }

    /**
     * Извлечение имени класса из полного имени.
     * @return имя класса
     */
    public String getClassName() {
        // Точка в случае пакетов
        int begin = this.classFullName.lastIndexOf(".") + 1;
        int end = this.classFullName.length();

        // доллар в случае вмещающих классов
        int nestedBegin = this.classFullName.lastIndexOf("$") + 1;

        if (nestedBegin > begin) {
            begin = nestedBegin;
        }
        return this.classFullName.substring(begin, end);
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(this.className)) {
            // Начало чтения новой записи
            this.record = (ReadType) ReflectionHelper.createInstance(this.classFullName);
        } else if (this.record != null) {
            // Начало чтения нового поля (внутреннего тега)
            this.type = attributes.getValue("type");
            this.fieldValue.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // Считывание значения между тегами
        this.fieldValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (this.record != null) {
            if (qName.equals(this.className)) {
                // окончание чтения записи
                this.records.put(this.record.getUnique(), this.record);
                this.record = null;
            } else if (this.record.getUniqueFields().contains(qName)) {
                // Задание значения уникального поля
                ReflectionHelper.setFieldValue(this.record,
                        "String", qName, this.fieldValue.toString());
            } else {
                // Задание значения обычного поля (type м.б. null)
                ReflectionHelper.setFieldValue(this.record,
                        this.type, qName, this.fieldValue.toString());
            }
        }
    }

    /**
     * Возврат результата работы обработчика.
     * @return контейнер с прочитанными данными в виде объектов типа ReadType.
     */
    public Map<String, ReadType> getData() {
        return this.records;
    }
}
