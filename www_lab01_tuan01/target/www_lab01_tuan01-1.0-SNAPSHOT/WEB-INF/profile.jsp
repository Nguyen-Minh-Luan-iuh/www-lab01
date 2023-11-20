<%--
  Created by IntelliJ IDEA.
  User: HOME
  Date: 11/20/2023
  Time: 3:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>

<style>
    *, *::after, *::before{
        padding: 0;
        margin: 0;
        box-sizing: border-box;
    }

    body{
        height: 100vh;
        position: relative;
        background: brown;
    }

    header{
        padding: 20px;
        background-color: bisque;
        display: flex;
        justify-content: space-between;
    }

    .wrapper{
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    .profile{
        width: 550px;
        background: bisque;
        padding: 20px;
        border-radius: 5px;
    }

    h1{
        text-align: center;
        font-size: 18px;
        margin-bottom: 25px;
    }

    .profile-row{
        display: flex;
        justify-content: space-between;
    }
    .profile-row:not(:last-child){
        margin-bottom: 15px;
    }

    ul{
        list-style: none;
    }
</style>
<body>
<header>
    <div> <p>Hi, ${account.fullName}</p></div>
    <div>
        <form method="post" action="controllerServlet">
            <input type="hidden" name="action" value="logout"/>
            <input type="hidden" name="logId" value="${log.id}"/>
            <button type="submit">Logout</button>
        </form>
    </div>
</header>

<div class="wrapper">
    <div class="profile">
        <h1>Profile</h1>
        <div class="profile-row">
            <p>Account Id</p>
            <p>${account.accountId}</p>
        </div>
        <div class="profile-row">
            <p>Full Name</p>
            <p>${account.fullName}</p>
        </div>
        <div class="profile-row">
            <p>Password</p>
            <p>${account.password}</p>
        </div>
        <div class="profile-row">
            <p>Email</p>
            <p>${account.email}</p>
        </div>
        <div class="profile-row">
            <p>Phone</p>
            <p>${account.phone}</p>
        </div>
        <div class="profile-row">
            <p>Status</p>
            <p>${account.status}</p>
        </div>
        <div class="profile-row">
            <p>Roles: </p>
            <ul>
                <c:forEach items="${account.roles}" var="role">
                    <li>${role.roleName}</li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
