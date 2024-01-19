package com.example.servlet;

import com.example.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final Users users;

    public LoginServlet() {
        this.users = Users.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isUserLoggedIn(req)) {
            resp.sendRedirect(req.getContextPath() + "/user/hello.jsp");
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isUserLoggedIn(req)) {
            String login = req.getParameter("login");
            String newUserName = getUserByUserCredentials(login, "");

            HttpSession session = req.getSession();

            if (newUserName != null) {
                session.setAttribute("user", newUserName);
            }
        }

        doGet(req, resp);
    }

    private String getUserByUserCredentials(String login, String password) {
        if (login != null) {
            Optional<String> username =
                    this.users.getUsers().stream()
                            .filter(item -> item.equalsIgnoreCase(login))
                            .findFirst();

            if (username.isPresent() && password != null && password.isEmpty()) {
                return username.get();
            }
        }
        return null;
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("user") != null;
    }
}
