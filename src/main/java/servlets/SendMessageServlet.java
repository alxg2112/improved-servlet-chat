package servlets;

import entities.Message;
import repository.Repository;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that gets message from the client and submits it to the repository.
 */
@WebServlet(name = "SendMessageServlet", asyncSupported = true)
public class SendMessageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(900000000);
        String from = request.getParameter("from");
        String text = request.getParameter("message");
        Message message;
        message = new Message(from, text);
        Repository.submitMessage(message);
        asyncContext.complete();
    }
}
