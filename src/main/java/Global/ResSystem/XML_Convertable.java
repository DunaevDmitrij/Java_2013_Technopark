package Global.ResSystem;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 02.12.13
 * Time: 13:21
 */

/**
 *  Интерфейс для представления xml данных.
 *  Каждый класс, который абстрагирует запись (т. е. отображает свои поля на теги xml и наоборот) и
 *  используется вместе с ресурсной системой должен его реализовать.
 */

public interface XML_Convertable {
    String getUnique();              // Значение уникального поля объекта (м.б. полей)
    String getUniqueFields();        // Имена уникальных полей объекта (если несколько, то записать через пробел)
}
