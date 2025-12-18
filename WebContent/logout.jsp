<div class="logout-container">
    <a href="<%= request.getContextPath() %>/account" class="account-btn">Account</a>
    <a href="<%= request.getContextPath() %>/logout" class="logout-btn">Logout</a>
</div>

<style>
.logout-container {
    position: absolute;
    top: 20px;
    right: 20px;
}

.account-btn {
    padding: 8px 14px;
    background: #444;
    color: white;
    text-decoration: none;
    border-radius: 6px;
    font-weight: bold;
    margin-right: 10px;
}

.account-btn:hover {
    background: #222;
}

.logout-btn {
    padding: 8px 14px;
    background: #e31837;
    color: white;
    text-decoration: none;
    border-radius: 6px;
    font-weight: bold;
}

.logout-btn:hover {
    background: #b6122d;
}
</style>
