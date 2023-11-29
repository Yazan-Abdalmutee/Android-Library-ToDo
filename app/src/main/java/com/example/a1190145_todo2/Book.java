package com.example.a1190145_todo2;

public class Book {
    private int bookID;
    private String bookTitle;
    private String bookAuthor;
    private String publicationDate;
    public Book()
    {

    }

    public Book( String bookTitle, String bookAuthor, String publicationDate) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.publicationDate = publicationDate;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getPublicationDate() {
        return this.publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}
