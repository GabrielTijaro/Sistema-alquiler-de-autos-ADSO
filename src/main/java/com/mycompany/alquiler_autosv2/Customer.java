/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alquiler_autosv2;

public class Customer {
    private String id;
    private String name;
    private String phone;

    public Customer(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Name: " + name +
               " | Phone: " + phone;
    }
}