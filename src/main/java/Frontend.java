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
        String responseText = "";

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        responseText +=  "<html><head><title>Hello server!</title></head><body>\n";
        //response.getWriter().println("<html><head><title>Hello server!</title></head><body>");
        //response.getWriter().println("Hello!");
        //TODO question: is variant with String really quicker?
        responseText += "Hello!\n";
        //response.getWriter().println("</body></html>");
        responseText += "</body></html>";

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            userId = userIdGenerator.getAndIncrement();
            session.setAttribute("userId", userId);


        }
        response.getWriter().println(responseText);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {

    }

    private AtomicLong userIdGenerator = new AtomicLong();



}
