package com.mycompany.alquiler_autosv2;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String role; // ADMIN, EMPLOYEE, CASHIER

    public User(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }

    public boolean checkPassword(String candidate) {
        return this.password != null && this.password.equals(candidate);
    }

    @Override
    public String toString() {
        return fullName + " [" + role + "] (@" + username + ")";
    }
}
