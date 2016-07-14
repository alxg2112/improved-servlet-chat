package servlets;

import entities.Request;
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
@WebServlet(name = "RequestServlet")
public class RequestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getSession().getId();
        Request userRequest;
        String responseText;
        userRequest = new Request(from);
        Repository.submitRequest(userRequest);
//        responseText = userRequest.getResponse().toString() + "<br>";
//        response.getWriter().write(responseText);
        userRequest.dispatchResponse(response);
    }
}
