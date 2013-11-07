package Global;

import Global.MessageSystem.MessageSystem;
import Global.MessageSystem.Address;
import Global.MessageSystem.Abonent;
import Global.WebPages.WebPage;
import Global.WebPages.AuthPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * Author: artemlobachev
 * Date: 21.09.13
 * Time: 10:22
*/
public class Frontend extends HttpServlet implements Abonent, Runnable {

    // Здесь и далее: все URL-ы записываются как константы.
    protected static final String ADDRESS_AUTH = "/auth";

    private final MessageSystem ms;
    private final Address address;
    private final SessionService sessionService;

    public Frontend(MessageSystem ms) {
        super();
        this.ms = ms;
        //получаем адрес для Frontend
        this.address = new Address();
        // регистрируем Frontend в AddressService
        this.ms.getAddressService().setFrontend(this.address);
        //регистрируем Frontend в MessageSystem
        ms.addService(this);
        this.sessionService = new SessionService(ms);
    }

    /**
     * Выводим количество обращений каждые 5 секунд.
     */
    @Override
    public void run() {
        while (true) {
            this.ms.execForAbonent(this);

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Создание объекта страницы в зависимости от переданного URL.
     * @param Path строка-параметр для сопоставления
     * @return объект WebPage с нужной реализацией
     */
    public WebPage createPage(String Path) {
        if (Path.equals(ADDRESS_AUTH)) {
            return new AuthPage(this.sessionService);
        } else {
            return null;
        }
    }

    //имплементация интерфейса Abonent
    @Override
    public Address getAddress() {
        return this.address;
    }

    /**
     * Обрабатываем GET запрос.
     * @param request запрос
     * @param response ответ
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException TODO написать откуда может появиться!
     */
     @Override
     public void doGet(HttpServletRequest request,
                HttpServletResponse response)
                throws IOException, ServletException {


        // TODO: Пока предполагается, что всегда возвращаем html.
        // TODO: Если иначе, то перенести в WebPage
        response.setContentType("text/html;charset=utf-8");

        // Создание объекта страницы, в зависимости от запрашиваемого URL
        WebPage page = this.createPage(request.getPathInfo());
        if (page == null) {
            // Обработка неизвестного URL
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            // Получение страницы строкой. Выполняет анализ сессии, выборку контента и генерацию страницы.
            String pageStr = page.handleGET(request);
            response.getWriter().println(pageStr);
            // Установка статуса после выполнения handleGET
            response.setStatus(page.getStatus());
        }
    }

    /**
     * Обрабатываем POST запрос.
     * @param request объект запроса
     * @param response объект ответа сервера
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException  TODO написать откуда может появиться!
     */
     @Override
     public void doPost(HttpServletRequest request,
            HttpServletResponse response)
     throws IOException, ServletException
     {
        response.setContentType("text/html;charset=utf-8");

        // Создание объекта страницы
        WebPage page = this.createPage(request.getPathInfo());
        if (page == null) {
            // Обработка неизвестного URL
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            // Генерация страницы
            String pageStr = page.handlePOST(request);
            response.getWriter().println(pageStr);
            // Установка статуса
            response.setStatus(page.getStatus());
        }
    }

}
