package Global;

import Global.MsgSystem.Messages.Msg;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 30.11.13
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public class Utilities {

    /**
     * Метод для линеаризации процесса занесения данных в Map<>.
     * @param keys массив строковых ключей
     * @param values соответствующие значения полей в контейнере (любой тип)
     * @return Новосозданный контейнер
     */
    public static Map<String, Object> dataToKey(String[] keys, Object ... values) {
        Map<String, Object> map = new HashMap<>();
        // Цикл по элементам массива ключей
        for (int I = 0; I < keys.length; ++I) {
            map.put(keys[I], values[I]);
        }
        return map;
    }

    public static void sendMsgInTime(final MessageSystem messageSystem, final Msg msg, final long timeSeconds){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeSeconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messageSystem.sendMessage(msg);
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

}
