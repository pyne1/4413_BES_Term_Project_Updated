package controller;

import dao.ProductDao;
import model.Cart;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)   throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Object user = (session != null) ? session.getAttribute("currentCustomer") : null;

        //user must be logged in 
        if (user == null) {
            req.getRequestDispatcher("forceLogin.jsp").forward(req, resp);
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
      
        
        // add new cart if there isnt a current session going on
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String todo = req.getParameter("todo");

        if (todo == null) {
            req.getRequestDispatcher("cart.jsp").forward(req, resp);
            return;
        }

        switch (todo) {
            case "add":
                String itemID = req.getParameter("itemID");
                Product p = ProductDao.getById(itemID);
                
                
                if (p != null) {
                    cart.addItem(p);
                }
               
                
                session.setAttribute("cartCount", cart.getTotalQuantity());
                String referer = req.getHeader("referer");
                resp.sendRedirect(referer);
                break;

                
                
                
            case "remove":
                cart.removeItem(req.getParameter("itemID"));
                session.setAttribute("cartCount", cart.getTotalQuantity());
                resp.sendRedirect("cart");
                break;
                
                

            case "update":
                String id = req.getParameter("itemID");
                int qty = Integer.parseInt(req.getParameter("qty"));
                cart.updateQuantity(id, qty);
                session.setAttribute("cartCount", cart.getTotalQuantity());
                resp.sendRedirect("cart");
                break;

                
                
            default:
                req.getRequestDispatcher("cart.jsp").forward(req, resp);
        }
    }
}
