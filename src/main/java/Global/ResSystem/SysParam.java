package Global.ResSystem;

import org.apache.xml.serializer.SerializationHandler;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 02.12.13
 * Time: 13:12
 */

/**
 * Класс представляющий системный параметр приложения.
 * Имеет уникальное имя и поле значения, тип которого передается параметром типа.
 * @param <ValueType> тип поля значения системного параметра
 */
public class SysParam<ValueType> implements XML_Convertable {
    private String name;         // unique
    private ValueType value;     // Значение системного параметра

    public SysParam() {
        super();
    }

    // Инициализирующий конструктор
    public SysParam(String name, ValueType value) {
        super();
        this.name = name;
        this.value = value;
    }

    @Override
    public String getUnique() {
        return this.name;
    }

    @Override
    public String getUniqueFields() {
        return "name";
    }

    /**
     *  Можно получить значение
      */
    public ValueType getValue() {
        return this.value;
    }
}
