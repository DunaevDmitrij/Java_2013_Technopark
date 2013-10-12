import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 12.10.13
 * Time: 8:47
 * To change this template use File | Settings | File Templates.
 */

abstract public class WebPage {
    protected int Status;

    abstract public String handleGET(HttpServletRequest request, Map<String, Long> users);
    abstract public String handlePOST(HttpServletRequest request, Map<String, Long> users);

    public static WebPage createPage(String Path) {
        if (Path.equals("/auth"))
            return new AuthPage();
        else if (Path.equals("/test"))
            return new AuthPage();
        else
            return null;
    }

    protected String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss"); // здесь задаем формат времени
        return formatter.format(date);
    }

    protected String generatePage(String templateName, Map<String, Object> context) {
        return PageGenerator.getPage(templateName, context);
    }

    public int getStatus() {
        return Status;
    }
}
