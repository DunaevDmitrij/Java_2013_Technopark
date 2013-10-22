import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;

/**
 * Author: artemlobachev
 * Date: 19.10.13
 */
@SuppressWarnings("deprecation") //JUnit 4 doesn't like class Assert.
public class MessageSystemTest {

    private MessageSystem ms;

    @Before
    public void setUp() throws Exception {
        this.ms = new MessageSystem();
    }

    @Test
    public void testGetAddressService(){
        final String ErrText = "AddressService is null";
        Assert.assertNotNull(ErrText, this.ms.getAddressService());
    }

    @Test
    public void testSendMessageToNullAddress(){
        final String ErrText = "Bad processing for null address";
        Assert.assertFalse(ErrText, this.ms.sendMessage(new MsgToReciver(null, null, null)));
    }

    @Test
    public void testSendMessageToUnknownAddress(){
        final String ErrText = "Bad processing for unknown address";
        Assert.assertFalse(ErrText, this.ms.sendMessage(new MsgToReciver(null, new Address(), null)));
    }

    @Test
    public void testSendMessage() throws Exception {
        final String tstMessage = "Hello!";
        final String ErrText = "Error in sendMessage() or execForAbonent()!";
        Reciver reciver = new Reciver(this.ms);
        this.ms.sendMessage(new MsgToReciver(null, reciver.getAddress(),tstMessage));
        this.ms.execForAbonent(reciver);

        Assert.assertEquals(ErrText, reciver.getMessage(), tstMessage);

    }

    private class MsgToReciver extends Msg{

        private final String message;

        private MsgToReciver(Address from, Address to, String message) {
            super(from, to);
            this.message = message;
        }

        @Override
        void exec(Abonent abonent) {
            if(abonent instanceof Reciver){
                ((Reciver) abonent).setMessage(this.message);
            }
        }
    }

    private class Reciver implements Abonent{
        private final Address address;
        private String message;
        @SuppressWarnings("UnusedDeclaration")
        private final MessageSystem ms;

        public Reciver(MessageSystem ms){
            super();
            this.address = new Address();
            this.ms = ms;
            ms.addService(this);
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }


        @Override
        public Address getAddress() {
            return this.address;
        }
    }
}
