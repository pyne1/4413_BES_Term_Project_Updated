package dao;

import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.Product;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, customerEmail, total, orderDate FROM orders ORDER BY orderDate DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                int orderId = rs.getInt("id");

                o.setId(orderId);
                o.setCustomerEmail(rs.getString("customerEmail"));
                o.setTotal(rs.getDouble("total"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setItems(getItemsForOrder(conn, orderId));

                orders.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private static List<OrderItem> getItemsForOrder(Connection conn, int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT itemID, productName, quantity, price " +
                     "FROM order_items WHERE order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setItemID(rs.getString("itemID"));
                    item.setProductName(rs.getString("productName"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    items.add(item);
                }
            }
        }

        return items;
    }

    public static void saveOrder(String customerEmail, Cart cart) {
        String insertOrderSql = "INSERT INTO orders (customerEmail, total, orderDate) " +
                                "VALUES (?, ?, CURRENT_TIMESTAMP)";
        String insertItemSql = "INSERT INTO order_items " +
                "(order_id, itemID, productName, quantity, price) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement psOrder = conn.prepareStatement(
                     insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {

            psOrder.setString(1, customerEmail);
            psOrder.setDouble(2, cart.getTotal());
            psOrder.executeUpdate();

            int orderId = 0;
            try (ResultSet keys = psOrder.getGeneratedKeys()) {
                if (keys.next()) {
                    orderId = keys.getInt(1);
                }
            }

            try (PreparedStatement psItem = conn.prepareStatement(insertItemSql)) {
                for (CartItem ci : cart.getItems()) {
                    Product p = ci.getProduct();

                    psItem.setInt(1, orderId);
                    psItem.setString(2, p.getItemID());
                    psItem.setString(3, p.getName());
                    psItem.setInt(4, ci.getQuantity());
                    psItem.setDouble(5, p.getPrice());

                    psItem.addBatch();
                }
                psItem.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
