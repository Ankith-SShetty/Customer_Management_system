package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.DAO.UserDAO;



@WebServlet("/AuthServlet")
@MultipartConfig
public class AuthServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login_id = req.getParameter("login_id");
        String password = req.getParameter("password");
        System.out.println("Login ID: " + login_id + "\n Password: " + password);

        String authPassword = userDAO.authenticateUser(login_id);

        if (authPassword.equals(password)) {
            String token = JwtUtil.generateToken(login_id);
//            resp.getHeader("application/json");
            resp.setHeader("Access-Control-Allow-Origin", "http://your-frontend-url");
            resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);

            resp.getWriter().write("{\"token\":\"" + token + "\"}");
            System.out.println("Generated Token: " + token);

        }
        else{
            System.out.println("Invalid credentials for Login ID: " + login_id);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write("Invalid credentials");

        }

    }
}
