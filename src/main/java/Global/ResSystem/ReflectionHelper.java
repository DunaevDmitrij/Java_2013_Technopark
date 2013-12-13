package Global.ResSystem;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 23.11.13
 * Time: 9:44
 */

public class ReflectionHelper {

    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (SecurityException |
                InstantiationException |
                IllegalAccessException |
                ClassNotFoundException |
                IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Получить объект некоего переданного типа.
     * @param className имя запрашиваемого класса.
     * @return экземпляр типа.
     */
    public static Object createInstanceWithArgs(String className, Object ... args) {
        try {
            Class<?>[] arg_types = new Class<?>[args.length];
            int I = 0;
            for (Object arg : args) {
                arg_types[I] = arg.getClass();
                I++;
            }

            return Class.forName(className).getConstructor(arg_types).newInstance(args);
        } catch (SecurityException |
                InstantiationException |
                IllegalAccessException |
                ClassNotFoundException |
                InvocationTargetException |
                NoSuchMethodException |
                IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Установка значения поля для переданного объекта
     * Пока работает только для строк и чисел.
     *
     * @param object у кого устанавливаем поле
     * @param fieldType тип поля (для generic-ов)
     * @param name имя устанавливаемого поля
     * @param value новое значение
     */
    public static void setFieldValue(Object object, String fieldType, String name, String value) {
        try {
            Field field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);

            Class<?> type = Class.forName(fieldType);

            System.out.println(object + "|" + type + "|" + name + "|" + value);

            if(type.equals(String.class)) {
                field.set(object, value);
            } else if (type.equals(Integer.class)) {
                field.set(object, Integer.decode(value));
            } else if (type.equals(Boolean.class)) {
                field.set(object, Boolean.valueOf(value));
            }

            field.setAccessible(false);
        } catch (SecurityException |
                ClassNotFoundException |
                IllegalAccessException |
                NoSuchFieldException |
                IllegalArgumentException |
                NullPointerException e) {
            e.printStackTrace();
        }
    }
}