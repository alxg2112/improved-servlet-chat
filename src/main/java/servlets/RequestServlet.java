package servlets;

import entities.Request;
import repository.Repository;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that gets request from the client, submits it to the repository and sends response.
 */
@WebServlet(name = "RequestServlet", asyncSupported = true)
public class RequestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(900000000);
        String from = request.getSession().getId();
        Request userRequest;
        userRequest = new Request(from);
        Repository.submitRequest(userRequest);
        userRequest.dispatchResponse(asyncContext, response);
    }
}
