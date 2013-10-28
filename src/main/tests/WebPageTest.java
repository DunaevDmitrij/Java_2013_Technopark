import Global.WebPages.WebPage;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 26.10.13
 * Time: 9:30
 * To change this template use File | Settings | File Templates.
 */

@SuppressWarnings("deprecation") //JUnit 4 doesn't like class Assert.
public class WebPageTest {
    private TestPage testInstance;

    protected class TestPage extends WebPage {
        @Override
        public String handleGET(HttpServletRequest request) {
            return null;
        }

        @Override
        public String handlePOST(HttpServletRequest request) {
            return null;
        }

        public String generatePageRemote(String templateName, Map<String, Object> context) {
            return this.generatePage(templateName, context);
        }

        public String generatePageRemote(String templateName) {
            return this.generatePage(templateName);
        }
    }

    @Before
    public void setUp() throws Exception {
        this.testInstance = new TestPage();
    }

    @Test
    public void testGeneratePageContext() throws Exception {
        final String ErrText = "Error with generating of page with context. ";

        Map<String, Object> context = new HashMap<>();
        context.put("UserName", "ModulTest");
        context.put("UserId", "+inf");
        final String Result = this.testInstance.generatePageRemote("test.tml", context);
        Map<String, Integer> checkArr = checkPage(Result);
        //FIXME: кажется, тут логическая ошибка - обрати внимание, что такое checkArr, то есть что ты по факту сравнивешь с -1
        for (String current : checkArr.keySet())
            Assert.assertNotSame(ErrText + "Page doesn't contain " + checkArr.get(current) + ".", -1, checkArr);
    }

    @Test
    public void testGeneratePage() throws Exception {
        final String ErrText = "Error with generating of page without context";

        final String result = this.testInstance.generatePageRemote("wait.tml");
        Map<String, Integer> checkArr = checkPage(result);
        //FIXME: кажется, тут логическая ошибка - обрати внимание, что такое checkArr, то есть что ты по факту сравнивешь с -1
        for (String current : checkArr.keySet())
            Assert.assertNotSame(ErrText + "Page doesn't contain " + checkArr.get(current) + ".", -1, checkArr);
    }

    private Map<String, Integer> checkPage(String strPage) {
        //FIXME:если мы хотим проверять хоть какую-то логику, а не формальный запуск,тут для разных методов будут разные значения
        final String cmps[] = { "<html>", "<head>", "<body>" };
        Map<String, Integer> result = new HashMap<>();
        for (String current : cmps) {
            result.put(current, strPage.indexOf(current));
        }
        return result;
    }
}
