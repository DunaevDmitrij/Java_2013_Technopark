package Global;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: artemlobachev
 * Date: 09.11.13
 */
public interface WebPage {
    String WAITING = "";

    /** Методы для генерации страниц по соответствующему запросу.
     *  Должны анализировать сессию, создавать контекст и вызывать генерацию (generatePage()).
     * @param request объект запроса, для получения данных сессии
     * @return  возвращает сгенерированную страницу
     */
    String handleGET(HttpServletRequest request);

    String handlePOST(HttpServletRequest request);

    int getStatus();

}
