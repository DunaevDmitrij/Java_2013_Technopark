import Global.Imps.SessionServiceImp;
import Global.Imps.UserSession;
import Global.MessageSystem;
import Global.MsgSystem.MessageSystemImp;
import Global.SessionService;
import Global.WebPages.AuthPage;
import org.eclipse.jetty.server.session.HashedSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

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
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void setUp() {
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HashedSession.class);
        when(this.request.getSession()).thenReturn(this.session);

        MessageSystem ms = new MessageSystemImp();
        SessionService sessionService = new SessionServiceImp(ms);
        this.testInstance = new AuthPage(sessionService);
    }

    @Test
    public void test_1() {
        when(this.session.getAttribute("sessionId")).thenReturn(null);
        String strPage = this.testInstance.handleGET(this.request);
        final String ErrText = "Error in handling get with simple entry of new user: ";

        int checkPage = strPage.indexOf("<input type=\"text\" name=\"name\">");
        Assert.assertNotSame(ErrText, checkPage, -1);
    }

    @Test
    public void test_2() {
        when(this.session.getAttribute("sessionId")).thenReturn(0);

        String strPage = this.testInstance.handleGET(this.request);
        final String ErrText = "Error in handling get with authorizing of user: ";

        int checkPage_1 = strPage.indexOf("Здравствуйте");
        int checkPage_2 = strPage.indexOf("Здравствуйте");
        Assert.assertNotSame(ErrText, checkPage_1, -1);
        Assert.assertNotSame(ErrText, checkPage_2, -1);
    }
}
