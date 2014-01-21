package jetty.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-9
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class JettyContainer {


    public static void main(String[] args) throws Exception {
        Server server = new Server();
        //threadpool
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(100);
        threadPool.setMinThreads(10);
        threadPool.setDetailedDump(false);

        server.setThreadPool(threadPool);

        //connector
        Connector connector = new SelectChannelConnector();
        connector.setPort(8080);  //端口
        server.setConnectors(new Connector[]{connector});
        String dirPath = System.getProperty("user.dir");
        String webPath = System.getProperty("webapp.root");
        String pathStr = JettyContainer.class.getClassLoader().getResource("").getPath();
        WebAppContext context = new WebAppContext("", "/test");
        server.setHandler(context);
        server.setStopAtShutdown(true);
        server.setSendServerVersion(true);
        server.start();
        server.join();
    }


}
