<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Required</title>
    <style>
        body { font-family: Arial, sans-serif; background:#f4f4f4; margin:0; padding:40px; }
        .box { max-width:600px; margin:auto; background:#fff; padding:25px; border-radius:10px; border:1px solid #ddd; }
        h2 { margin-top:0; color:#E31837; }
        a.btn {
            display:inline-block; margin-right:10px; margin-top:10px;
            padding:10px 14px; border-radius:6px; text-decoration:none;
            background:#E31837; color:#fff; border:1px solid #000;
        }
        a.btn:hover { background:#C41230; }
    </style>
</head>
<body>
<div class="box">
    <h2>Please sign in to continue</h2>
    <p>You need an account to complete checkout.</p>

    <a class="btn" href="<%= request.getContextPath() %>/login">Sign In</a>
    <a class="btn" href="<%= request.getContextPath() %>/register">Create Account</a>
    <a class="btn" href="<%= request.getContextPath() %>/cart">Back to Cart</a>
</div>
</body>
</html>
