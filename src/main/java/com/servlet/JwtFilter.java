package com.servlet;

import com.servlet.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
          
            String token = authHeader.substring(7);

            try {
                if (JwtUtil.validateToken(token)) { 
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                System.err.println("Token validation failed: " + e.getMessage());
            }
        }
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("{\"error\":\"Unauthorized access\"}");
    }

    @Override
    public void destroy() {
        // Any cleanup logic can go here
    }
}
