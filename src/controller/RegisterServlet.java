package controller;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (firstName == null || lastName == null || email == null || password == null ||
                firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
                email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Customer created = CustomerDAO.register(firstName.trim(), lastName.trim(), email.trim(), password);

        if (created == null) {
            request.setAttribute("error", "Could not create account. Email may already exist.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("currentCustomer", created);

        String redirect = (String) session.getAttribute("redirectAfterLogin");
        if (redirect != null) {
            session.removeAttribute("redirectAfterLogin");
            response.sendRedirect(request.getContextPath() + "/" + redirect);
        } else {
            response.sendRedirect(request.getContextPath() + "/catalog?view=all");
        }
    }
}
