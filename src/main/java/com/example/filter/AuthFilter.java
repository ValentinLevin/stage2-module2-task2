package com.example.filter;

import com.example.Users;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/user/*")
public class AuthFilter extends HttpFilter {
    private Users users;

    public AuthFilter() {
        this.users = Users.getInstance();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String username = null;

        HttpSession session = req.getSession(false);
        if (session != null) {
            username = (String) session.getAttribute("user");
        }

        if (username == null || !this.users.getUsers().contains(username)) {
            if (session != null) {
                session.removeAttribute("user");
            }
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}