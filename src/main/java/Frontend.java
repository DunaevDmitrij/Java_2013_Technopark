import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 21.09.13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public class Frontend extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Hello!");

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            userId = userIdGenerator.getAndIncrement();
            session.setAttribute("userId", userId);


        }
        response.getWriter().println(userId);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {

    }

    private AtomicLong userIdGenerator = new AtomicLong();



}
