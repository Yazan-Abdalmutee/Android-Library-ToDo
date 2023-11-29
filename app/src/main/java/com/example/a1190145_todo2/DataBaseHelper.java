package com.example.a1190145_todo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the  tables when the database is created
        sqLiteDatabase.execSQL("CREATE TABLE BOOK(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT, AUTHOR TEXT,PUBLICATION_DATE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE BORROWER(ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRST_NAME TEXT, LAST_NAME TEXT,MEMBERSHIP_EXPIRY_DATE TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE BORROWING(ID INTEGER PRIMARY KEY AUTOINCREMENT,BORROWER_ID INTEGER, BOOK_ID INTEGER,BORROWING_DATE TEXT,RETURNED_DATE TEXT,DUE_DATE TEXT)");
    }

    public void clearTables() {
        // Clear and recreate the tables
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BOOK");
        sqLiteDatabase.execSQL("CREATE TABLE BOOK(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, AUTHOR TEXT, PUBLICATION_DATE TEXT)");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BORROWER");
        sqLiteDatabase.execSQL("CREATE TABLE BORROWER(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, LAST_NAME TEXT, MEMBERSHIP_EXPIRY_DATE TEXT)");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS BORROWING");
        sqLiteDatabase.execSQL("CREATE TABLE BORROWING(ID INTEGER PRIMARY KEY AUTOINCREMENT,BORROWER_ID INTEGER, BOOK_ID INTEGER,BORROWING_DATE TEXT,RETURNED_DATE TEXT,DUE_DATE TEXT)");
        sqLiteDatabase.close();
    }

    // Insert a book  into the database
    public void insertBook(Book book) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", book.getBookTitle());
        contentValues.put("AUTHOR", book.getBookAuthor());
        contentValues.put("PUBLICATION_DATE", book.getPublicationDate());
        sqLiteDatabase.insert("BOOK", null, contentValues);
    }

    // Insert a borrower  into the database
    public void insertBorrower(Borrower borrower) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRST_NAME", borrower.getBorrowerFirstName());
        contentValues.put("LAST_NAME", borrower.getBorrowerLastName());
        contentValues.put("MEMBERSHIP_EXPIRY_DATE", borrower.getBorrowerMembershipExpiryDate());
        sqLiteDatabase.insert("BORROWER", null, contentValues);
    }

    // Insert a borrowing  into the database
    public void insertBorrowing(Borrowing borrowing) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BORROWER_ID", borrowing.getBorrowerID());
        contentValues.put("BOOK_ID", borrowing.getBookID());
        contentValues.put("BORROWING_DATE", borrowing.getBorrowingDate());
        contentValues.put("RETURNED_DATE", borrowing.getReturnedDate());
        contentValues.put("DUE_DATE", borrowing.getDueDate());
        sqLiteDatabase.insert("BORROWING", null, contentValues);
    }

    // get all books from the database
    public Cursor getAllBooks() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM BOOK", null);
    }

    // get all borrowers from the database
    public Cursor getAllBorrowers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM BORROWER", null);
    }

    // get a borrower by member ID from the database
    public Cursor getBorrowersById(int memberId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM BORROWER WHERE ID = ?", new String[]{String.valueOf(memberId)});
    }

    // get all borrowings from the database
    public Cursor getAllBorrowings() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM BORROWING", null);
    }

    // get all borrowings for a  borrower ID from the database
    public Cursor getAllBorrowingsById(int memberId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM BORROWING WHERE BORROWER_ID = ?", new String[]{String.valueOf(memberId)});
    }

    // get all borrowings for a  book ID from the database
    public Cursor getAllBorrowingsByBookId(int bookId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM BORROWING WHERE BOOK_ID = ?", new String[]{String.valueOf(bookId)});
    }

    // get a book by ID from the database
    public Cursor getBookById(int bookId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM BOOK WHERE ID = ?", new String[]{String.valueOf(bookId)});
    }

    // Update  a borrowing date   by ID from the database
    public void updateBorrowingDataById(int borrowingId, String newReturnedData) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("RETURNED_DATE", newReturnedData);

        sqLiteDatabase.update("BORROWING", values, "ID = ?", new String[]{String.valueOf(borrowingId)});

        sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
