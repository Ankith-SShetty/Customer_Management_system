package com.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.DAO.CustomerDAO;
import com.connection.DBConnection;
import com.google.gson.Gson;
import com.model.Customer;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/customers")
@MultipartConfig
public class CustomerServlet extends HttpServlet {
    private List<Customer> customers = new ArrayList<>();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Handle customer creation
        Customer customer = new Customer();
        customer.setFirstName(request.getParameter("first_name"));
        customer.setLastName(request.getParameter("last_name"));
        customer.setAddress(request.getParameter("address"));
        customer.setCity(request.getParameter("city"));
        customer.setState(request.getParameter("state"));
        customer.setEmail(request.getParameter("email"));
        customer.setPhone(request.getParameter("phone"));

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO customers (first_name, last_name,  address, city, state, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getAddress());
                statement.setString(4, customer.getCity());
                statement.setString(5, customer.getState());
                statement.setString(6, customer.getEmail());
                statement.setString(7, customer.getPhone());
                statement.executeUpdate();
            }
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Customer> customers = new ArrayList<>();
        System.out.println("Inside Customer Servlet");
        String query = "SELECT * FROM customers";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customers.add(customer);
            }

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(customers));
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId;
        try {
            customerId = Integer.parseInt(request.getParameter("id"));
            deleteCustomer(customerId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid customer ID");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete customer");
        }
    }

    private void deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }
}



