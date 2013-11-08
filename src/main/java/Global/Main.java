package Global; /**
 * Author: artemlobachev
 * Date: 20.09.13
 * Time: 23:27
 */
import Global.Imps.*;
import Global.MsgSystem.MessageSystemImp;
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
    public static final int DEFAULT_SERVER_PORT = 8080;

    public static final String THREAD_NAME_FRONTEND = "Frontend";
    public static final String THREAD_NAME_ACCOUNT_SERVICE = "AS";
    public static final String THREAD_NAME_SESSION_SERVICE = "SS";


    public static void main(String args[ ])throws Exception {
        //проверяем наличие параметра порт. Если не передан - запускаемся на порту по умолчанию(переменная DEFAULT_SERVER_PORT).
        int port;
        if (args.length < 1) {
            System.out.append("No port in parametrs. Using default port " + DEFAULT_SERVER_PORT + ".\n");
            port = DEFAULT_SERVER_PORT;
        }else{
            String portString = args[0];
            port = Integer.valueOf(portString);
            System.out.append("Starting at port: ").append(portString).append('\n');
        }

        MessageSystem ms = new MessageSystemImp();

        SessionService sessionService = new SessionServiceImp(ms);
        Frontend frontend = new Frontend(ms, sessionService);
        AccountService accountService = new AccountServiceImp(ms);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //создаем пул потоков и добавляем в него наши Frontend, Account Service и Session Service
        ThreadPool threadPool = new ThreadPoolImp();
        threadPool.startThread(frontend, THREAD_NAME_FRONTEND);
        threadPool.startThread(accountService, THREAD_NAME_ACCOUNT_SERVICE);
        threadPool.startThread(sessionService, THREAD_NAME_SESSION_SERVICE);

        //скармливаем его серверу

        AdminPageServlet adminPageServlet = new AdminPageServlet();
        context.addServlet(new ServletHolder(adminPageServlet), AdminPageServlet.adminPageURL);
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
