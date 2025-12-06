package controller;

import dao.ProductDao;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/catalog")
public class ProductListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("currentCustomer") : null;

        if (user == null) {
            request.getRequestDispatcher("forceLogin.jsp").forward(request, response);
            return;
        }

        String view = request.getParameter("view");
        String brand = request.getParameter("brand");
        String category = request.getParameter("category");

        List<Product> products;

        if ("brand".equalsIgnoreCase(view) && brand != null && !brand.isEmpty()) {
            products = ProductDao.getByBrand(brand);
        } else if ("category".equalsIgnoreCase(view) && category != null && !category.isEmpty()) {
            products = ProductDao.getByCategory(category);
        } else {
            view = "all";
            products = ProductDao.getAllProducts();
        }

        request.setAttribute("products", products);
        request.setAttribute("brands", ProductDao.getAllBrands());
        request.setAttribute("categories", ProductDao.getAllCategories());
        request.setAttribute("selectedView", view);
        request.setAttribute("selectedBrand", brand);
        request.setAttribute("selectedCategory", category);

        request.getRequestDispatcher("items.jsp").forward(request, response);
    }
}
