/**
 * Author: artemlobachev
 * Date: 20.09.13
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

    public static final String MAIN_PAGE_ADDRESS = "/auth";
    public static final String STATIC_DIR = "static";
    public static final int SERVER_PORT = 8080;
    public static final String THREAD_NAME_FRONTEND = "Frontend";
    // --Commented out by Inspection (12.10.13, 21:08):public static final Object threadsMonitor = new Object();


    public static void main(String args[ ])throws Exception {
        Frontend frontend = new Frontend();
        System.out.println("Main thread ID=" +  Thread.currentThread().getId());

        Server server = new Server(SERVER_PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //создаем пул потоков и добавляем в него наш Frontend
        ThreadPool threadPool = new ThreadPool();
        threadPool.startThread(frontend, THREAD_NAME_FRONTEND);

        //скармливаем его серверу
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
