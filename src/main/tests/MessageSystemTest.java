import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;

/**
 * Author: artemlobachev
 * Date: 19.10.13
 */
public class MessageSystemTest {

    private MessageSystem ms;
    private Reciver reciver;

    @Before
    public void setUp() throws Exception {
        this.ms = new MessageSystem();
        this.reciver = new Reciver(this.ms);
    }

    @Test
    public void testSendMessage() throws Exception {
        final String tstMessage = "Hello!";
        final String ErrText = "Error in sendMessage() or execForAbonent()!";
        this.ms.sendMessage(new MsgToReciver(null, this.reciver.getAddress(),tstMessage));
        this.ms.execForAbonent(this.reciver);

        Assert.assertEquals(ErrText, this.reciver.getMessage(), tstMessage);

    }

    private class MsgToReciver extends Msg{

        private String message;

        private MsgToReciver(Address from, Address to, String message) {
            super(from, to);
            this.message = message;
        }

        @Override
        void exec(Abonent abonent) {
            if(abonent instanceof Reciver){
                ((Reciver) abonent).setMessage(message);
            }
        }
    }

    private class Reciver implements Abonent{
        private Address address;
        private String message;
        private MessageSystem ms;

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
