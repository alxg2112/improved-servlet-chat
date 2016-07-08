package servlets;

import entities.Message;
import repository.Repository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alexander on 07.07.2016.
 */
@WebServlet(name = "SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getParameter("from");
        String text = request.getParameter("message");
        Message message;
        message = new Message(from, text);
        Repository.submitMessage(message);
    }
}
