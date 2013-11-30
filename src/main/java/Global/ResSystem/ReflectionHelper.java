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

    /**
     * Получить объект некоего переданного типа.
     * @param className имя запрашиваемого класса.
     * @return экземпляр типа.
     */
    public static Object createIntance(String className, Object ... args) {
        try {
            Class<?>[] arg_types = new Class<?>[args.length];
            int I = 0;
            for (Object arg : args) {
                arg_types[I] = arg.getClass();
                I++;
            }

            return Class.forName(className).getConstructor(arg_types).newInstance(args);
        } catch (IllegalArgumentException |
                 SecurityException |
                 IllegalAccessException |
                 InstantiationException |
                 InvocationTargetException |
                 NoSuchMethodException |
                 ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Установка значения поля для переданного объекта
     * Пока работает только для строк и чисел.
     *
     * @param object у кого устанавливаем поле
     * @param fieldName имя устанавливаемого поля
     * @param value новое значение
     */
    public static void setFieldValue(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if(field.getType().equals(String.class)) {
                field.set(object, value);
            } else if (field.getType().equals(int.class)) {
                field.set(object, Integer.decode(value));
            }

            field.setAccessible(false);
        } catch (SecurityException |
                 NoSuchFieldException |
                 IllegalArgumentException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}