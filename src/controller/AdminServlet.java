package controller;

import dao.ProductDao;
import dao.CustomerDAO;
import dao.OrderDAO;
import model.Product;
import model.Customer;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        
        
        if (session == null || session.getAttribute("currentCustomer") == null) {
            
        	response.sendRedirect("login.jsp");
            return;
        }

        Customer current = (Customer) session.getAttribute("currentCustomer");
        if (!"admin@everything.yorku.ca".equalsIgnoreCase(current.getEmail())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String section = request.getParameter("section");

        
        
        if (section == null) {
        	section = "inventory";
        }

        if ("sales".equals(section)) {
            List<Order> orders = OrderDAO.getAllOrders();
            request.setAttribute("orders", orders);
        }
        
        else if ("users".equals(section)) {
            List<Customer> customers = CustomerDAO.getAllCustomers();
            request.setAttribute("customers", customers);
        } else {
            List<Product> products = ProductDao.getAllProducts();
            request.setAttribute("products", products);
        }
        
        

          request.setAttribute("section", section);
        
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,   HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
       
        // must be logged in to access the thrift store
        if (session == null || session.getAttribute("currentCustomer") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Customer current = (Customer) session.getAttribute("currentCustomer");
       
        // only admin account can access this functionality
        if (!"admin@everything.yorku.ca".equalsIgnoreCase(current.getEmail())) {
            response.sendRedirect("login.jsp");
            return;
        }

        
        
        String action = request.getParameter("action");

        
        if ("updateProduct".equals(action)) {

            String itemID = request.getParameter("itemID");
            String name = request.getParameter("name");
            String brand = request.getParameter("brand");
            String category = request.getParameter("category");

            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product p = new Product();

            int pid = Integer.parseInt(itemID);
            p.setProductId(pid);

            p.setItemID(itemID);
            p.setName(name);
            p.setBrand(brand);
            p.setCategory(category);
            p.setPrice(price);
            p.setQuantity(quantity);

            Product existing = ProductDao.getById(itemID);
            if (existing != null) {
                p.setDescription(existing.getDescription());
                p.setImageUrl(existing.getImageUrl());
            }

            ProductDao.updateProduct(p);
            response.sendRedirect("admin?section=inventory");
            return;
        }


        if ("addProduct".equals(action)) {
            String name = request.getParameter("name");
            String brand = request.getParameter("brand");
            String category = request.getParameter("category");
            
            
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product p = new Product();
            
            
            p.setName(name);
            p.setBrand(brand);
            p.setCategory(category);
            p.setPrice(price);
            p.setQuantity(quantity);

           
            ProductDao.addProduct(p);
            response.sendRedirect("admin?section=inventory");
            return;
        }

        if ("updateUser".equals(action)) {
            
        	int id = Integer.parseInt(request.getParameter("id"));
            
        	
        	String firstName = request.getParameter("firstName");
                      
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            
            Customer c = new Customer();
            
            
            
            c.setId(id);
            c.setFirstName(firstName);
            c.setLastName(lastName);
            c.setEmail(email);

            
            
            CustomerDAO.updateCustomer(c);
            response.sendRedirect("admin?section=users");
            return;
        }

        
        
        response.sendRedirect("admin");
    }
}
