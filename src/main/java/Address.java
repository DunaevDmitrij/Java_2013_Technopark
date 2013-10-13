import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public class Address {
    static private AtomicInteger abonentIdCreator = new AtomicInteger();
    final private int abonentId;

    public Address(){
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    @Override
    public int hashCode(){
        return abonentId;
    }
}
