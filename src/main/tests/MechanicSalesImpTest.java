import Global.Imps.DBServiceImp;
import Global.MechanicSales;
import Global.MessageSystem;
import Global.MsgSystem.MessageSystemImp;
import Global.Ticket;
import Global.mechanics.MechanicSalesImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MechanicSalesImpTest {
    private MessageSystem messageSystem;
    private DBServiceImp dbServiceImp;
    private MechanicSalesImp mechanicSales;

    @Before
    public void setUp() throws SQLException {
        messageSystem = new MessageSystemImp();
        dbServiceImp = new DBServiceImp(this.messageSystem, "TestPlaneDB");
        Thread thread = new Thread(dbServiceImp);
        thread.start();
        mechanicSales = new MechanicSalesImp(this.messageSystem);
        thread = new Thread(mechanicSales);
        thread.start();
    }

    @Test
    public void searchTest() throws InterruptedException {
        final String ErrText = "Search works wrong";
        HashMap<String, String> params = new HashMap<>();
        params.put(MechanicSales.findParams.ARRIVAL_AIRPORT,"Аэропрт_вылета1");
        params.put(MechanicSales.findParams.DEPARTURE_AIRPORT,"Аэропрт_вылета1");
        params.put(MechanicSales.findParams.DEPARTURE_DATE_TIME_SINCE, "0");
        Collection<Ticket> rez = mechanicSales.search(params);
        Thread.sleep(1000);
        Assert.assertNull(ErrText, rez);
        params.put(MechanicSales.findParams.DEPARTURE_DATE_TIME_TO,"1000");
        rez = mechanicSales.search(params);
        Thread.sleep(1000);
        Assert.assertEquals(ErrText, rez.size(),1);
        System.out.println(rez.iterator().next().getArrivalAirport());
        System.out.println(rez.size());
    }

}
