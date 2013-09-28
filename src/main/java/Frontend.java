import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
                      HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        //baseRequest.setHandled(true);

        //текущая сессия
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null)
        {
            Map<String, Object> pageVariables = new HashMap<>();
            response.getWriter().println(PageGenerator.getPage("auth.tml", pageVariables));
        }
        else
        {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("UserID", userId);
            pageVariables.put("Time", getTime());
            String name = (String) session.getAttribute("userName");
            pageVariables.put("User", name);
            String sessionId = (String) session.getId();
            pageVariables.put("Session", sessionId);
            response.getWriter().println(PageGenerator.getPage("test.tml", pageVariables));
        }
    }

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        //baseRequest.setHandled(true);

        if (request.getPathInfo().equals("/auth"))
        {
            String name = (String) request.getParameter("login");

            if (name.equals("mist"))
            {
                HttpSession session = request.getSession();
                Long userId = 0L;//(Long) session.getAttribute("userId");
                //userId = userIdGenerator.getAndIncrement();

                Map<String, Object> pageVariables = new HashMap<>();
                session.setAttribute("userId", userId);
                session.setAttribute("userName", name);
                pageVariables.put("UserID", 0);
                pageVariables.put("Time", getTime());
                pageVariables.put("User", name);
                String sessionId = (String) session.getId();
                pageVariables.put("Session", sessionId);
                response.getWriter().println(PageGenerator.getPage("test.tml", pageVariables));
            }
            else
            {
                response.getWriter().println("Wrong user");
            }
        }
    }


    private AtomicLong userIdGenerator = new AtomicLong();



}
