<%@ page import="model.Cart" %>
<%@ include file="adminbutton.jsp" %>


<%
    Cart cartIcon = (Cart) session.getAttribute("cart");
    int cartCount = 0;

    if (cartIcon != null) {
        cartCount = cartIcon.getTotalQuantity();
    }

    session.setAttribute("cartCount", cartCount);
%>

<div style="position:relative; float:right; margin:10px;">
    <a href="cart">
        <img src="https://cdn-icons-png.flaticon.com/512/1170/1170678.png"
             width="32" height="32" alt="Cart">
    </a>

    <span style="
        position:absolute;
        top:-6px;
        right:-6px;
        background:#E31837;
        color:white;
        font-size:12px;
        padding:2px 6px;
        border-radius:50%;
    ">
        <%= cartCount %>
    </span>
</div>
