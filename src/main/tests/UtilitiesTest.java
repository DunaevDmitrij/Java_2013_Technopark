import Global.Address;
import Global.MessageSystem;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.MessageSystemImp;
import Global.MsgSystem.Messages.Msg;
import Global.Utilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Thread.sleep;

/**
 * Author: artemlobachev
 * Date: 16.12.13
 */
public class UtilitiesTest {
    private static final long SLEEP_TIME = 10;
    private MessageSystem messageSystem;

    @Before
    public void setUp(){
        this.messageSystem = new MessageSystemImp();
    }

    @Test
    public void testSendMsgInTime() throws InterruptedException {
        final String ErrText1 = "Something wrong with the test";
        final String ErrText2 = "Utilities.sendMsgInTime() works wrong";
        Reciver reciver = new Reciver(this.messageSystem);
        Thread reciverThread = new Thread(reciver);
        reciverThread.start();
        Assert.assertFalse(ErrText1, reciver.getTestField());
        Utilities.sendMsgInTime(this.messageSystem,new MsgToReciver(null, reciver.getAddress()), SLEEP_TIME);
        Thread.sleep(SLEEP_TIME*100);
        Assert.assertTrue(ErrText2, reciver.getTestField());

    }

    private class MsgToReciver extends Msg{
        public MsgToReciver(Address from, Address to){
            super(from, to);
        }

        @Override
        public void exec(Abonent abonent) {
            if(abonent instanceof Reciver){
                ((Reciver) abonent).setTestField(true);
            }
        }
    }

    private class Reciver implements Abonent, Runnable{
        private MessageSystem ms;
        private Address address;
        private boolean testField;


        public Reciver(MessageSystem ms){
            this.testField = false;
            this.ms = ms;
            this.address = new Address();
            this.ms.addService(this);
        }

        @Override
        public Address getAddress() {
            return this.address;
        }

        public boolean getTestField() {
            return this.testField;
        }

        public void setTestField(boolean testField) {
            this.testField = testField;
        }

        @Override
        public void run() {
            while (true) {
                this.ms.execForAbonent(this);

                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
