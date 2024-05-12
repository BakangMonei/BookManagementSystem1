package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Monei Bakang Mothuti
 * @Date: Sunday 12 May 2024
 * @Time: 4:29 PM
 */
public class Customer {
    private String name, address, email;
    private List<Book> purchaseHistory;

    public Customer(){
        super();
    }

    public Customer(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.purchaseHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getPurchaseHistory() {
        return purchaseHistory;
    }

    // Method to add a book to purchase history
    public void addPurchase(Book book) {
        purchaseHistory.add(book);
    }

    // toString method to represent Customer object as String
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", purchaseHistory=" + purchaseHistory +
                '}';
    }
}
