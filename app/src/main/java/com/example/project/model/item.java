package com.example.project.model;

public class item {

    private int id;
    private String Bookname;
    private String BookAuthor;
    private int Bookprice;
    private String dateItemAdded;

    public item() {
    }

    public item(String bookname, String bookAuthor, int bookprice) {
        Bookname = bookname;
        BookAuthor = bookAuthor;
        Bookprice = bookprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public int getBookprice() {
        return Bookprice;
    }

    public void setBookprice(int bookprice) {
        Bookprice = bookprice;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }
}

