import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;

/**
 * Author: artemlobachev
 * Date: 19.10.13
 */
public class MessageSystemTest {

    private MessageSystem ms;

    @Before
    public void setUp() throws Exception {
        this.ms = new MessageSystem();
        Abonent tstAbonentSender = new AccountService(this.ms);
        Abonent tstAbonentResiver = new Frontend(this.ms);
        this.ms.addService(tstAbonentSender);
        this.ms.addService(tstAbonentResiver);
        this.ms.getAddressService().setAccountService(tstAbonentSender.getAddress());
    }

    @Test
    public void testSendMessage() throws Exception {
        this.ms.sendMessage(new MsgGetUserId(this.ms.getAddressService().getAccountService(),this.ms.getAddressService().getAccountService(),"vasia",0L));
        Assert.assertTrue(true);

    }
}
