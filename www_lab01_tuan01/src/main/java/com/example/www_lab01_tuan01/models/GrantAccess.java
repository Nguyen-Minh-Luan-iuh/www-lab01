package com.example.www_lab01_tuan01.models;

public class GrantAccess {
    private String accountId;
    private String roleId;
    private int isGrant;
    private String note;

    public GrantAccess() {
    }

    public GrantAccess(String accountId, String roleId, int isGrant, String note) {
        this.accountId = accountId;
        this.roleId = roleId;
        this.isGrant = isGrant;
        this.note = note;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public int getIsGrant() {
        return isGrant;
    }

    public void setIsGrant(int isGrant) {
        this.isGrant = isGrant;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "GrantAccess{" +
                "accountId='" + accountId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", isGrant=" + isGrant +
                ", note='" + note + '\'' +
                '}';
    }
}
