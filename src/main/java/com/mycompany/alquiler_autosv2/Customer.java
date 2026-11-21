package com.mycompany.alquiler_autosv2;

public class Customer {
    private String id;
    private String name;
    private String phone;
    private String address;

    public Customer(String id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Customer(String id, String name, String phone) {
        this(id, name, phone, "Neiva, Huila");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "CC/ID: " + id +
               " | Nombre: " + name +
               " | Tel: " + phone +
               " | Dir: " + address;
    }
}
