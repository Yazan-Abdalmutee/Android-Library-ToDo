package com.example.a1190145_todo2;

public class Borrower {
    private int borrowerID;
    private String borrowerFirstName;
    private String borrowerLastName;
    private String borrowerMembershipExpiryDate;

    public Borrower()
    {

    }

    public Borrower(String borrowerFirstName, String borrowerLastName, String borrowerMembershipExpiryDate) {

        this.borrowerFirstName = borrowerFirstName;
        this.borrowerLastName = borrowerLastName;
        this.borrowerMembershipExpiryDate = borrowerMembershipExpiryDate;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getBorrowerFirstName() {
        return borrowerFirstName;
    }

    public void setBorrowerFirstName(String borrowerFirstName) {
        this.borrowerFirstName = borrowerFirstName;
    }

    public String getBorrowerLastName() {
        return borrowerLastName;
    }

    public void setBorrowerLastName(String borrowerLastName) {
        this.borrowerLastName = borrowerLastName;
    }

    public String getBorrowerMembershipExpiryDate() {
        return borrowerMembershipExpiryDate;
    }

    public void setBorrowerMembershipExpiryDate(String borrowerMembershipExpiryDate) {
        this.borrowerMembershipExpiryDate = borrowerMembershipExpiryDate;
    }
}
