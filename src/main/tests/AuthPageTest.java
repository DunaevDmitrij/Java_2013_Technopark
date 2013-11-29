import Global.WebPages.AuthPage;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 23.11.13
 * Time: 15:16
 */

public class AuthPageTest {
    private AuthPage testInstance;

    @Before
    public void setUp() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getPathInfo()).thenReturn();
    }

    @Test
    public void test_1() {
    }
}
