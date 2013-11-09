package Global;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:15
 */
public class Address {
    private static final AtomicInteger abonentIdCreator = new AtomicInteger();
    private final int abonentId;

    public Address() {
        super();
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    @Override
    public int hashCode() {
        return this.abonentId;
    }
}
