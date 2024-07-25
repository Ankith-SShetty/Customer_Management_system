package com.servlet;

import com.servlet.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Any filter initialization logic can go here
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the token
            String token = authHeader.substring(7);

            try {
                // Validate the token
                if (JwtUtil.validateToken(token)) {  // Assuming you have a method that validates the token
                    // Token is valid; continue to the next filter or servlet
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                // Handle token validation exception (if needed)
                System.err.println("Token validation failed: " + e.getMessage());
            }
        }

        // If we reach here, authentication failed; respond with 401 Unauthorized
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("{\"error\":\"Unauthorized access\"}"); // Send JSON error response
    }

    @Override
    public void destroy() {
        // Any cleanup logic can go here
    }
}
