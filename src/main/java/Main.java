/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 20.09.13
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */

import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;



public class Main {
    public static String STATIC_DIR = "static";
    public static int SERVER_PORT = 8080;
    public static String MAIN_PAGE_ADDRESS = "/test";


    public static void main(String args[ ])throws Exception {
        Frontend frontend = new Frontend();

        Server server = new Server(SERVER_PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(frontend), "/*");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(false); // не показывать содержание директории при переходе по /
        resource_handler.setResourceBase(STATIC_DIR); //путь к папке статики от корня проекта
        //переадресация пользователя с / на нужный нам адрес
        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(true);
        rewriteHandler.setOriginalPathAttribute("requestedPath");
        RedirectRegexRule rule = new RedirectRegexRule();
        rule.setRegex("/");//здесь устанавливаем откуда перенаправлять
        rule.setReplacement(MAIN_PAGE_ADDRESS);//а здесь - куда
        rewriteHandler.addRule(rule);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{rewriteHandler, resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
