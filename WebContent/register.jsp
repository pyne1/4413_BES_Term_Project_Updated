<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>

    <!-- Link to the shared YorkU theme CSS -->
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <div class="container">

        <h1>Create Your Account</h1>

        <% 
            String error = (String) request.getAttribute("error"); 
            if (error != null) { 
        %>
            <p style="color:red"><%= error %></p>
        <% 
            } 
        %>

        <form action="<%= request.getContextPath() %>/register" method="post">

            <!-- FIRST NAME -->
            <label>First Name:</label><br>
            <input type="text" name="firstName" required><br><br>

            <!-- LAST NAME -->
            <label>Last Name:</label><br>
            <input type="text" name="lastName" required><br><br>

            <!-- EMAIL -->
            <label>Email:</label><br>
            <input type="email" name="email" required><br><br>

            <!-- PASSWORD -->
            <label>Password:</label><br>
            <input type="password" name="password" required><br><br>

            <hr style="margin: 18px 0;">

            <!-- PAYMENT INFO -->
            <h3 style="margin: 0 0 10px 0;">Payment Info</h3>

            <label>Credit Card Number:</label><br>
            <input type="text" name="creditCardNumber" maxlength="50" required><br><br>

            <label>Expiry(MM/YY):</label><br>
            <input type="text" name="creditCardExpiry" maxlength="10" placeholder="MM/YY" required><br><br>

            <label>CVV:</label><br>
            <input type="text" name="creditCardCVV" maxlength="10" required><br><br>

            <hr style="margin: 18px 0;">

            <!-- BILLING ADDRESS -->
            <h3 style="margin: 0 0 10px 0;">Billing Address</h3>

            <label>Billing Address:</label><br>
            <input type="text" name="billingAddress" maxlength="255" required><br><br>

            <label>Billing City:</label><br>
            <input type="text" name="billingCity" maxlength="100" required><br><br>

            <label>Billing Postal Code:</label><br>
            <input type="text" name="billingPostal" maxlength="20" required><br><br>

            <hr style="margin: 18px 0;">

            <!-- SHIPPING ADDRESS -->
            <h3 style="margin: 0 0 10px 0;">Shipping Address</h3>

            <label>Shipping Address:</label><br>
            <input type="text" name="shippingAddress" maxlength="255" required><br><br>

            <label>Shipping City:</label><br>
            <input type="text" name="shippingCity" maxlength="100" required><br><br>

            <label>Shipping Postal Code:</label><br>
            <input type="text" name="shippingPostal" maxlength="20" required><br><br>

            <button type="submit" class="btn">Create Account</button>
        </form>

        <div class="footer">
            Already have an account? <a href="login.jsp">Sign In</a>
        </div>

    </div>
</body>
</html>
