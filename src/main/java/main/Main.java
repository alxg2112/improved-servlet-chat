package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.RequestServlet;
import servlets.SendMessageServlet;

/**
 * Main class that deploys the app on Jetty server.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final Server server = new Server(8085);

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(RequestServlet.class, "/request");
        context.addServlet(SendMessageServlet.class, "/send");

        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("./target/classes/");
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        server.setHandler(
                new HandlerList(){{
                    setHandlers(new Handler[]{resourceHandler, context});
                }}
        );

        server.start();
        server.join();
    }
}
