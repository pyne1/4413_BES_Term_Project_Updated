package controller;

import dao.ProductDao;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/product")
public class ProductDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect("catalog");
            return;
        }

        Product p = ProductDao.getById(id);
        if (p == null) {
            response.sendRedirect("catalog");
            return;
        }

        request.setAttribute("product", p);
        request.getRequestDispatcher("itemDetail.jsp").forward(request, response);
    }
}
