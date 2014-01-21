package jetty.server;


import jetty.servlets.HelloServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-9
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public class DefaultServer {

    public static void main(String[] args) throws Exception {

        Server server = getServer(8080);
        server.start();

    }


    public static Server getServer(int port) {
        Server server = new Server(port);
        ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        root.addServlet(new ServletHolder(new HelloServlet()), "/sayhello");
        return server;
    }

}
