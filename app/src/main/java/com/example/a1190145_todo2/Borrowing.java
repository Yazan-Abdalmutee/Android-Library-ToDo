package com.example.a1190145_todo2;

public class Borrowing {
    private int borrowingID;
    private int borrowerID;
    private int bookID;
    private String borrowingDate;
    private String returnedDate;
    private String dueDate;

    public Borrowing()
    {

    }

    public Borrowing( int borrowerID, int bookID, String borrowingDate, String returnedDate, String dueDate) {
        this.borrowerID = borrowerID;
        this.bookID = bookID;
        this.borrowingDate = borrowingDate;
        this.returnedDate = returnedDate;
        this.dueDate = dueDate;
    }

    public int getBorrowingID() {
        return borrowingID;
    }

    public void setBorrowingID(int borrowingID) {
        this.borrowingID = borrowingID;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        borrowingDate = borrowingDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        returnedDate = returnedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        dueDate = dueDate;
    }
}
