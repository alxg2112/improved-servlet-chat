package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.RequestServlet;
import servlets.SendMessageServlet;

/**
 * Main class that deploys the app on Jetty server.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase("src/main/java/");
        server.setHandler(context);

        // Add dump servlet
        context.addServlet(RequestServlet.class, "/request");
        // Add default servlet
        context.addServlet(SendMessageServlet.class, "/send");
        context.setWelcomeFiles(new String[]{"index.html"});

        server.start();
        server.join();
    }
}
