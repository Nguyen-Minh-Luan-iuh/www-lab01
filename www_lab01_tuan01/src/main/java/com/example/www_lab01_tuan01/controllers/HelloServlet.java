package com.example.www_lab01_tuan01.controllers;

import java.io.*;
import java.sql.SQLException;

import com.example.www_lab01_tuan01.services.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private AccountService accountService;

    public void init() throws ServletException {
        try{
            this.accountService = new AccountService();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        switch (action){
            case "showAddAccount": {
                this.accountService.showAddAccount(request, response);
                break;
            }

            case "showLoginForm": {
                this.accountService.showLoginForm(request, response);
            }

            case "showUpdateAccount": {
                this.accountService.showUpdateAccount(request, response);
                break;
            }

            case "showAddRole": {
                this.accountService.showAddRole(request, response);
                break;
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        switch (action){
            case "login": {
                this.accountService.login(req, resp);
                break;
            }

            case "logout": {
                this.accountService.logout(req, resp);
                break;
            }

            case "addAccount": {
                this.accountService.addAccount(req, resp);
                break;
            }

            case "addRole": {
                this.accountService.addRole(req, resp);
                break;
            }

            case "deleteUser": {
                this.accountService.deleteUser(req, resp);
                break;
            }

            case "updateAccount": {
                this.accountService.updateAccount(req, resp);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void destroy() {
    }
}