<%--
  Created by IntelliJ IDEA.
  User: HOME
  Date: 11/20/2023
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Update account</title>
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

  .wrapper{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  form{
    background-color: bisque;
    padding: 20px;
    width: 600px;
  }

  h1{
    text-align: center;
    font-size: 18px;
  }

  .form-group{
    display: flex;
    flex-direction: column;
    gap: 5px;
  }

  .form-group:not(:last-child){
    margin-bottom: 10px;
  }

  input{
    padding: 5px;
  }

  button{
    padding: 5px;
  }
</style>
<body>
<div class="wrapper">
  <form method="post" action="controllerServlet">
    <input type="hidden" name="action" value="updateAccount"/>

    <input type="hidden" name="logId" value="${logId}"/>
    <h1>Update account</h1>
    <div class="form-group">
      <label>Account Id</label>
      <input type="text" name="accountId" value="${account.accountId}"/>
    </div>
    <div class="form-group">
      <label>Full Name</label>
      <input type="text" name="fullName" value="${account.fullName}"/>
    </div>
    <div class="form-group">
      <label>Password</label>
      <input type="password" name="password" value="${account.password}"/>
    </div>
    <div class="form-group">
      <label>Email</label>
      <input type="email" name="email" value="${account.email}"/>
    </div>
    <div class="form-group">
      <label>Phone</label>
      <input type="text" name="phone" value="${account.phone}"/>
    </div>
    <div class="form-group">
      <c:forEach items="${roles}" var="role">
        <div>
          <label>${role.roleName}</label>
          <input type="checkbox" value="${role.roleId}" name="roles"/>
        </div>
      </c:forEach>
    </div>
    <div class="form-group">
      <button type="submit">Add account</button>
    </div>
  </form>
</div>
</body>
</html>
