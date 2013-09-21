/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 20.09.13
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */

import org.eclipse.jetty.server.Server;


public class Hello  {
    public static void main(String args[ ])throws Exception {
        Server server = new Server(8080);
        server.setHandler(new JettyServer());

        server.start();
        server.join();

    }
}
