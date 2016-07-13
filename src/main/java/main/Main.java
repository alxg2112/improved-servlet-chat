package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.RequestServlet;
import servlets.SendMessageServlet;

/**
 * Created by Alexander on 13.07.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        server.setHandler(context);

        // Add dump servlet
        context.addServlet(RequestServlet.class, "/request");
        // Add default servlet
        context.addServlet(SendMessageServlet.class, "/send");
        context.setWelcomeFiles(new String[]{"/web/index.html"});

        server.start();
        server.join();
        System.out.print("Press enter to stop server...");
        System.in.read();
        server.stop();
//        Server server = new Server(8080);
//
//        // Setup JMX
//        MBeanContainer mbContainer = new MBeanContainer(
//                ManagementFactory.getPlatformMBeanServer());
//        server.addBean(mbContainer);
//
//        // The WebAppContext is the entity that controls the environment in
//        // which a web application lives and breathes. In this example the
//        // context path is being set to "/" so it is suitable for serving root
//        // context requests and then we see it setting the location of the war.
//        // A whole host of other configurations are available, ranging from
//        // configuring to support annotation scanning in the webapp (through
//        // PlusConfiguration) to choosing where the webapp will unpack itself.
//        WebAppContext webapp = new WebAppContext();
//        webapp.setContextPath("/");
//        File warFile = new File(
//                "C:\\Users\\Alex\\IdeaProjects\\ServletWebChat\\out\\artifacts\\ServletWebChat_war_exploded");
//        webapp.setWar(warFile.getAbsolutePath());
//
//        // A WebAppContext is a ContextHandler as well so it needs to be set to
//        // the server so it is aware of where to send the appropriate requests.
//        server.setHandler(webapp);
//
//        // Start things up!
//        server.start();
//
//        // The use of server.join() the will make the current thread join and
//        // wait until the server is done executing.
//        // See http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
//        server.join();
    }
}
