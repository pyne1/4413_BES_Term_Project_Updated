<%@ page import="model.Customer" %>

<%
    Customer current = (Customer) session.getAttribute("currentCustomer");
    boolean isAdmin = false;

    if (current != null && "admin@everything.yorku.ca".equalsIgnoreCase(current.getEmail())) {
        isAdmin = true;
    }
%>

<% if (isAdmin) { %>
    <a href="admin" style="color:red; font-weight:bold; margin-left:10px; text-decoration:none;">
        Admin
    </a>
<% } %>
