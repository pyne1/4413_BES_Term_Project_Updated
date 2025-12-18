package dao;

import model.Customer;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getInt("id"));
        c.setFirstName(rs.getString("firstName"));
        c.setLastName(rs.getString("lastName"));
        c.setEmail(rs.getString("email"));
        c.setPassword(rs.getString("passwordHash"));
        c.setCreditCardNumber(rs.getString("creditCardNumber"));
        c.setCreditCardExpiry(rs.getString("creditCardExpiry"));
        c.setCreditCardCVV(rs.getString("creditCardCVV"));

        c.setBillingAddress(rs.getString("billingAddress"));
        c.setBillingCity(rs.getString("billingCity"));
        c.setBillingPostal(rs.getString("billingPostal"));

        c.setShippingAddress(rs.getString("shippingAddress"));
        c.setShippingCity(rs.getString("shippingCity"));
        c.setShippingPostal(rs.getString("shippingPostal"));
        return c;
    }

    public static Customer findByEmail(String email) {
        String sql = "SELECT * FROM customer WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean createCustomer(Customer c) {
        String sql = "INSERT INTO customer " +
                "(firstName, lastName, email, passwordHash, " +
                "creditCardNumber, creditCardExpiry, creditCardCVV, " +
                "billingAddress, billingCity, billingPostal, " +
                "shippingAddress, shippingCity, shippingPostal) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPassword());

            ps.setString(5, c.getCreditCardNumber());
            ps.setString(6, c.getCreditCardExpiry());
            ps.setString(7, c.getCreditCardCVV());

            ps.setString(8, c.getBillingAddress());
            ps.setString(9, c.getBillingCity());
            ps.setString(10, c.getBillingPostal());

            ps.setString(11, c.getShippingAddress());
            ps.setString(12, c.getShippingCity());
            ps.setString(13, c.getShippingPostal());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        c.setId(keys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static Customer validateLogin(String email, String plainPassword) {
        Customer c = findByEmail(email);
        if (c != null && plainPassword != null && plainPassword.equals(c.getPassword())) {
            return c;
        }
        return null;
    }

    public static boolean updateCustomer(Customer c) {
        String sql = "UPDATE customer SET " +
                "firstName = ?, lastName = ?, " +
                "creditCardNumber = ?, creditCardExpiry = ?, creditCardCVV = ?, " +
                "billingAddress = ?, billingCity = ?, billingPostal = ?, " +
                "shippingAddress = ?, shippingCity = ?, shippingPostal = ? " +
                "WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());

            ps.setString(3, c.getCreditCardNumber());
            ps.setString(4, c.getCreditCardExpiry());
            ps.setString(5, c.getCreditCardCVV());

            ps.setString(6, c.getBillingAddress());
            ps.setString(7, c.getBillingCity());
            ps.setString(8, c.getBillingPostal());

            ps.setString(9, c.getShippingAddress());
            ps.setString(10, c.getShippingCity());
            ps.setString(11, c.getShippingPostal());

            ps.setInt(12, c.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT id, firstName, lastName, email FROM customer";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setFirstName(rs.getString("firstName"));
                c.setLastName(rs.getString("lastName"));
                c.setEmail(rs.getString("email"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Customer register(
            String firstName,
            String lastName,
            String email,
            String passwordHash,
            String creditCardNumber,
            String creditCardExpiry,
            String creditCardCVV,
            String billingAddress,
            String billingCity,
            String billingPostal,
            String shippingAddress,
            String shippingCity,
            String shippingPostal
    ) {
        Customer existing = findByEmail(email);
        if (existing != null) return null;

        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setEmail(email);
        c.setPassword(passwordHash);

        c.setCreditCardNumber(creditCardNumber);
        c.setCreditCardExpiry(creditCardExpiry);
        c.setCreditCardCVV(creditCardCVV);

        c.setBillingAddress(billingAddress);
        c.setBillingCity(billingCity);
        c.setBillingPostal(billingPostal);

        c.setShippingAddress(shippingAddress);
        c.setShippingCity(shippingCity);
        c.setShippingPostal(shippingPostal);

        boolean ok = createCustomer(c);
        return ok ? c : null;
    }

}
