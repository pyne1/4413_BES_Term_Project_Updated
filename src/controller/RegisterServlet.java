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
        
        // NEW: payment + billing + shipping
        String creditCardNumber = request.getParameter("creditCardNumber");
        String creditCardExpiry = request.getParameter("creditCardExpiry");
        String creditCardCVV    = request.getParameter("creditCardCVV");

        String billingAddress = request.getParameter("billingAddress");
        String billingCity    = request.getParameter("billingCity");
        String billingPostal  = request.getParameter("billingPostal");

        String shippingAddress = request.getParameter("shippingAddress");
        String shippingCity    = request.getParameter("shippingCity");
        String shippingPostal  = request.getParameter("shippingPostal");

        if (isBlank(firstName) || isBlank(lastName) || isBlank(email) || isBlank(password)
                || isBlank(creditCardNumber) || isBlank(creditCardExpiry) || isBlank(creditCardCVV)
                || isBlank(billingAddress) || isBlank(billingCity) || isBlank(billingPostal)
                || isBlank(shippingAddress) || isBlank(shippingCity) || isBlank(shippingPostal)) {

            request.setAttribute("error", "All fields are required (account, payment, billing, shipping).");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Customer created = CustomerDAO.register(
                firstName.trim(),
                lastName.trim(),
                email.trim(),
                password,
                creditCardNumber.trim(),
                creditCardExpiry.trim(),
                creditCardCVV.trim(),
                billingAddress.trim(),
                billingCity.trim(),
                billingPostal.trim(),
                shippingAddress.trim(),
                shippingCity.trim(),
                shippingPostal.trim()
        );

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
    
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
