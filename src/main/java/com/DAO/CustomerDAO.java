//package com.DAO;
//
//import com.connection.DBConnection;
//import com.model.Customer;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomerDAO {
//
//    public static List<Customer> getAllCustomer(){
//        List<Customer> customers = new ArrayList<>();
//        String query = "SELECT * FROM customers";
//
//        try(
//                Connection con = DBConnection.getConnection();
//                Statement stmt = con.createStatement();
//                ResultSet res = stmt.executeQuery(query);
//                ){
//            while (res.next()){
//                Customer customer = new Customer();
//                customer.setFirstName(res.getString("first_name"));
//                customer.setLastName(res.getString("last_name"));
//                customer.setAddress(res.getString("address"));
//                customer.setCity(res.getString("city")); ;
//                customer.setState(res.getString("state"));;
//                customer.setEmail(res.getString("email"));
//                customer.setPhone(res.getString("phone"));
//                customers.add(customer);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return customers;
//    }
//}
