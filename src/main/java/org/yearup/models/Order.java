package org.yearup.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private int orderId;
    private int userId;
    private LocalDate date;
    private String address;
    private String state;
    private String zip;
    private BigDecimal shipping_amount;

    public Order(){}

    public Order(int orderId, int userId, LocalDate date, String address, String state, String zip, BigDecimal shipping_amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.date = date;
        this.address = address;
        this.state = state;
        this.zip = zip;
        this.shipping_amount = shipping_amount;
    }

    public BigDecimal getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(BigDecimal shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
