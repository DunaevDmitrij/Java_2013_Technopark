import Global.WebPages.WebPageImp;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 26.10.13
 * Time: 9:30
 */

/**
 * Модульные тесты для класса WebPage
 * (тестируются методы, которые затем наследуются остальными менеджерами страниц)
 */
@SuppressWarnings("deprecation") //JUnit 4 doesn't like class Assert.
public class WebPageTest {
    // Объект тестирования
    private TestPage testInstance;

    /**
     * Класс, наследник WbPage, для тестирования.
     * Используется для локального вскрытия закрытых и защищенных методов WebPage.
     */
    protected class TestPage extends WebPageImp {
        // Заглушки абстрактных методов
        @Override
        public int analyzeRequestGET(HttpServletRequest request) {
            return 0;
        }

        @Override
        public int analyzeRequestPOST(HttpServletRequest request) {
            return 0;
        }

        // Обертки для методов, которые будем тестировать
        public String generatePageRemote(String templateName, Map<String, Object> context) {
            return generatePage(templateName, context);
        }

        public String generatePageRemote(String templateName) {
            return generatePage(templateName);
        }
    }

    @Before
    public void setUp() {
        this.testInstance = new TestPage();
    }

    /**
     * Тест для метода генерации страницы с переданным контекстом.
     * @throws Exception
     */
    @Test
    public void testGeneratePageContext() throws Exception {
        final String ErrText = "Error in generating of page with context: ";

        // Создаем контекст
        Map<String, Object> context = new HashMap<>();
        context.put("UserName", "ModulTest");
        context.put("UserId", "+inf");
        // Вызываем целевой метод
        final String result = this.testInstance.generatePageRemote("test.tml", context);

        // Проверка результата по набору критериев (устанавливаются внутри checkPage)
        Map<String, Integer> checkArr = checkPage(result);
        // Печать результата
        for (String current : checkArr.keySet()) {
            Assert.assertNotSame(ErrText + current, checkArr.get(current), -1);
        }
    }

    /**
     * Тест для метода генерации отдачи страницы без контекста
     * @throws Exception
     */
    @Test
    public void testGeneratePage() throws Exception {
        final String ErrText = "Error in generating of page without context: ";

        // Вызов целевого метода
        final String result = this.testInstance.generatePageRemote("wait.tml");

        // Проверка результата
        Map<String, Integer> checkArr = checkPage(result);
        // Вывод
        for (String current : checkArr.keySet()) {
            Assert.assertNotSame(ErrText + current, checkArr.get(current), -1);
        }
    }

    /**
     * Проверка корректности html страницы
     * @param strPage текст html-страницы
     * @return отображение, которое содержит пары: имя критерия, результат проверки критерия (-1 - отрицательно)
     */
    private static Map<String, Integer> checkPage(String strPage) {
        //FIXME:если мы хотим проверять хоть какую-то логику, а не формальный запуск,тут для разных методов будут разные значения
        // Необходимые теги
        final String cmps[] = { "<html>", "<head>", "<body>" };

        Map<String, Integer> result = new HashMap<>();
        // Проверка наличия тегов в переданной строке
        for (String current : cmps) {
            result.put("Page doesn't contain " + current + ".", strPage.indexOf(current));
        }
        return result;
    }
}
