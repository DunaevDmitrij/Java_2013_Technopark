import Global.Imps.SessionServiceImp;
import Global.Imps.UserSession;
import Global.MessageSystem;
import Global.MsgSystem.MessageSystemImp;
import Global.MsgSystem.Messages.MsgGetUserId;
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
    private UserSession userSession;
    private SessionService sessionService;

    @Before
    public void setUp() {
        this.request = mock(HttpServletRequest.class);
        this.session = mock(HashedSession.class);
        this.userSession = mock(UserSession.class);
        when(this.request.getSession()).thenReturn(this.session);

        this.sessionService = mock(SessionService.class);
        this.testInstance = new AuthPage(this.sessionService);
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
        when(this.request.getParameter("name")).thenReturn("valera");
        when(this.session.getAttribute("sessionId")).thenReturn(0L);
        when(this.userSession.getName()).thenReturn("valera");
        when(this.userSession.getSessionId()).thenReturn(0L);
        when(this.userSession.isComplete()).thenReturn(true);
        when(this.sessionService.getUserInfo(0L)).thenReturn(this.userSession);

        String strPage = this.testInstance.handlePOST(this.request);
        System.out.println(strPage);
        String ErrText = "Error in handling get with authorizing of user: ";

        int checkPage = strPage.indexOf("Ждите авторизации.");
        Assert.assertNotSame(ErrText, checkPage, -1);

        //--------------
        strPage = this.testInstance.handleGET(this.request);
        System.out.println(strPage);

        ErrText = "Error in handling get with authorizing of user: ";

        int checkPage_1 = strPage.indexOf("Здравствуйте");
        int checkPage_2 = strPage.indexOf("ваш userId:");
        Assert.assertNotSame(ErrText, checkPage_1, -1);
        Assert.assertNotSame(ErrText, checkPage_2, -1);
    }
}
