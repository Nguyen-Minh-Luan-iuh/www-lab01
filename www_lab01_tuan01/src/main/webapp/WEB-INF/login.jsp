<%--
  Created by IntelliJ IDEA.
  User: HOME
  Date: 9/15/2023
  Time: 3:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<head>
  <title>Login</title>
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
    width: 450px;
    padding: 20px;
    background: bisque;
    border-radius: 5px;
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
    <input type="hidden" name="action" value="login"/>
    <h1>Login</h1>
    <div class="form-group">
      <label>Email: </label>
      <input type="email" name="email"/>
    </div>
    <div class="form-group">
      <label>Password: </label>
      <input type="password" name="password"/>
    </div>
    <div class="form-group">
      <button type="submit">Login</button>
    </div>
    <div class="form-group">
      <p>${error}</p>
    </div>
  </form>
</div>
</body>
</html>
