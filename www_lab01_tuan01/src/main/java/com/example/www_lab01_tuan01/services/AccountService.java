package com.example.www_lab01_tuan01.services;

import com.example.www_lab01_tuan01.models.Account;
import com.example.www_lab01_tuan01.models.GrantAccess;
import com.example.www_lab01_tuan01.models.Log;
import com.example.www_lab01_tuan01.models.Role;
import com.example.www_lab01_tuan01.repositoies.AccountRep;
import com.example.www_lab01_tuan01.repositoies.GrantAccessRep;
import com.example.www_lab01_tuan01.repositoies.LogRep;
import com.example.www_lab01_tuan01.repositoies.RoleRep;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AccountService {
    private AccountRep accountRep;
    private GrantAccessRep grantAccessRep;
    private LogRep logRep;
    private RoleRep roleRep;

    public AccountService() throws SQLException, ClassNotFoundException {
        this.accountRep = new AccountRep();
        this.grantAccessRep = new GrantAccessRep();
        this.logRep = new LogRep();
        this.roleRep = new RoleRep();
    }
    public void addAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        String fullName = req.getParameter("fullName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String[] selectedRoles = req.getParameterValues("roles");

        Account account = new Account();
        account.setAccountId(accountId);
        account.setPhone(phone);
        account.setFullName(fullName);
        account.setPassword(password);
        account.setEmail(email);

        if(this.accountRep.addAccount(account)){
            for(int i = 0; i < selectedRoles.length; i++){
                GrantAccess grantAccess = new GrantAccess(accountId, selectedRoles[i], 1, "");
                System.out.println(grantAccess);
                grantAccessRep.addGrantAccess(grantAccess);
            }
            List<Account> accounts = this.accountRep.getAll();
            Account currAccount = (Account) req.getSession().getAttribute("account");
            req.setAttribute("account", currAccount);
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<Account> result = accountRep.findByEmailAndPassword(email, password);
        if(!result.isPresent()){
            req.setAttribute("error", "Bad credentials");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        else{
            Account currentAccount = result.get();
            Log log = new Log((long)new Date().getTime(), currentAccount.getAccountId(),  "");
            List<GrantAccess> grantAccesses = this.grantAccessRep.getGrantAccessByAccountId(currentAccount.getAccountId());
            this.logRep.insertLog(log);
            boolean isAdmin = false;
            for(GrantAccess grantAccess : grantAccesses){
                Optional<Role> optionalRole = this.roleRep.findRoleById(grantAccess.getRoleId());
                currentAccount.addRole(optionalRole.get());
                if(grantAccess.getRoleId().equalsIgnoreCase("admin") && grantAccess.getIsGrant() == 1){
                    isAdmin = true;
                }
            }

            if(isAdmin){
                List<Account>accounts = this.accountRep.getAll();
                req.getSession().setAttribute("account", currentAccount);
                req.getSession().setAttribute("log", log);

                req.setAttribute("accounts", accounts);
                req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
            }
            else{
                req.getSession().setAttribute("account", currentAccount);
                req.getSession().setAttribute("log", log);
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
            }

        }
    }

    public void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Log log = (Log) req.getSession().getAttribute("log");
        this.logRep.updateLogoutTime(log.getId());
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }



    public void showAddAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Role> roles = this.roleRep.getAll();
        req.setAttribute("roles", roles);
        req.getRequestDispatcher("account_form.jsp").forward(req, res);
    }
    public void showUpdateAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        Optional<Account> existingAccount = this.accountRep.findById(accountId);
        if(!existingAccount.isPresent()){
            System.out.println("RUNNING");
            List<Account> accounts = this.accountRep.getAll();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }
        else{
            req.setAttribute("account", existingAccount.get());
            req.getRequestDispatcher("update_form.jsp").forward(req, res);
        }
    }

    public void deleteUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String accountId = req.getParameter("accountId");
        Account currentAccount = (Account) req.getSession().getAttribute("account");
        if(accountId.equalsIgnoreCase(currentAccount.getAccountId())){
            req.setAttribute("error", "Can't delete yourself");
            List<Account> accounts = this.accountRep.getAll();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }else{
            this.accountRep.deleteAccount(accountId);
            List<Account> accounts = this.accountRep.getAll();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("dashboard.jsp").forward(req, res);
        }
    }

    public void showLoginForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }



    public void updateAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Account updatingAccount = new Account();
        updatingAccount.setAccountId(req.getParameter("accountId"));
        updatingAccount.setPassword(req.getParameter("password"));
        updatingAccount.setPhone(req.getParameter("phone"));
        updatingAccount.setEmail(req.getParameter("email"));
        updatingAccount.setFullName(req.getParameter("fullName"));

        this.accountRep.updateAccount(updatingAccount);

        List<Account> accounts = this.accountRep.getAll();
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("dashboard.jsp").forward(req, res);
    }
    public void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = new Role();
        role.setStatus(1);
        role.setDescription(req.getParameter("description"));
        role.setRoleName(req.getParameter("roleName"));
        role.setRoleId(req.getParameter("roleId"));

        this.roleRep.addRole(role);

        List<Account> accounts = this.accountRep.getAll();
        Account currAccount = (Account) req.getSession().getAttribute("account");
        req.setAttribute("account", currAccount);
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }

    public void showAddRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("role_form.jsp").forward(req, resp);
    }


}
