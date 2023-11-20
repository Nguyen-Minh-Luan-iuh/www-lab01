<%--
  Created by IntelliJ IDEA.
  User: HOME
  Date: 9/15/2023
  Time: 3:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <title>Dashboard</title>
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

  .accounts{
    background-color: bisque;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  th, td{
    padding: 10px;
  }
</style>
<body>
<header>
  <div> <p>Hi, ${account.fullName}</p></div>
  <div>
    <a href="/controllerServlet?action=showAddAccount">Add account</a>
    <span>&nbsp;</span>
    <span>&nbsp;</span>
    <span>&nbsp;</span>
    <span>&nbsp;</span>
    <a href="/controllerServlet?action=showAddRole">Add role</a>
  </div>
  <div>
    <form method="post" action="controllerServlet">
      <input type="hidden" name="action" value="logout"/>
      <input type="hidden" name="logId" value="${log.id}"/>
      <button type="submit">Logout</button>
    </form>
  </div>
</header>

<div class="accounts">
  <p>${error}</p>
  <table border="1" width="800">
    <tr>
      <th>Account Id</th>
      <th>Full Name</th>
      <th>Email</th>
      <th>Phone</th>
      <th>Status</th>
      <th>Roles</th>
      <th>Action</th>
    </tr>

    <c:forEach items="${accounts}" var="account">
      <tr>
        <td>${account.accountId}</td>
        <td>${account.fullName}</td>
        <td>${account.email}</td>
        <td>${account.phone}</td>
        <td>${account.status}</td>
        <td>
          <c:forEach items="${account.roles}" var="role">
            <span>${role.roleName}</span>
            <br/>
          </c:forEach>
        </td>
        <td>
          <div>
            <form method="get" action="controllerServlet">
              <input type="hidden" name="action" value="showUpdateAccount"/>
              <input type="hidden" name="accountId" value="${account.accountId}"/>
              <input type="submit" value="Update"/>
            </form>
          </div>

          <div>
            <form method="post" action="controllerServlet">
              <input type="hidden" name="action" value="deleteUser"/>
              <input type="hidden" name="accountId" value="${account.accountId}"/>
              <input type="submit" value="Delete"/>
            </form>
          </div>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
