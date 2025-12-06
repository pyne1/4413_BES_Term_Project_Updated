package controller;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String firstName        = request.getParameter("firstName");
        String lastName         = request.getParameter("lastName");
        String email            = request.getParameter("email");
        String password         = request.getParameter("password");
       

        if (firstName == null || lastName == null || email == null || password == null ||
                firstName.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || password.isEmpty()) {

            request.setAttribute("error", "First name, last name, email, and password are required.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            Customer existing = CustomerDAO.findByEmail(email);
            if (existing != null) {
                request.setAttribute("error", "An account with that email already exists.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setPassword(password);
            


            boolean created = CustomerDAO.createCustomer(customer);

            if (!created) {
                request.setAttribute("error", "Could not create account. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("currentCustomer", customer);

            response.sendRedirect(request.getContextPath() + "/catalog?view=all");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
