package com.example.project;

public class UserHelperClass {

    String name, cardNo, expiryDate, cvc;
    int id;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String cardNo, String expiryDate, String cvc, int id) {
        this.name = name;
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
}



