package webubb.controller;

import webubb.domain.User;
import webubb.model.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Here");
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        RequestDispatcher rd = null;

        System.out.println(username + " " + password);
        DBManager dbManager = new DBManager();
        User user = dbManager.authenticate(username, password);
        if (user != null) {
            rd = request.getRequestDispatcher("/welcome.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }
        else {
            rd = request.getRequestDispatcher("/error.jsp");
        }
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
