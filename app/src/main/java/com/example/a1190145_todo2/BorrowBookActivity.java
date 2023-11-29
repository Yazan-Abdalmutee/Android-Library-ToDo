package com.example.a1190145_todo2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowBookActivity extends AppCompatActivity {

    LocalDate borrowingDate, dueDate, returnedDate, dateChecker, dueDateChecker, borrowingDateChecker;
    String TOAST_TEXT;
    //boolean to check if the book is busy or not
    boolean check = true;
    boolean exceptionOccurred = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book);
        Toolbar toolbar = findViewById(R.id.borrowBook_toolbar);
        setSupportActionBar(toolbar);
        //support back arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        EditText borrower_Id = findViewById(R.id.borrower_Id);
        EditText book_Id = findViewById(R.id.book_Id);

        //d:day m:month y:year

        EditText d_Due_edit = findViewById(R.id.d_Due_edit);
        EditText m_Due_edit = findViewById(R.id.m_Due_edit);
        EditText y_Due_edit = findViewById(R.id.y_Due_edit);

        Button confirm = findViewById(R.id.add_button);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exceptionOccurred = false;
                check = true;

                // s : String d:day m:month y:year
                String s_borrower_Id = borrower_Id.getText().toString();
                String s_book_Id = book_Id.getText().toString();




                String s_d_Due_edit = d_Due_edit.getText().toString();
                String s_m_Due_edit = m_Due_edit.getText().toString();
                String s_y_Due_edit = y_Due_edit.getText().toString();

                if (s_borrower_Id.isEmpty() || s_book_Id.isEmpty() || s_d_Due_edit.isEmpty() || s_m_Due_edit.isEmpty() || s_y_Due_edit.isEmpty()) {
                    TOAST_TEXT = "Please fill all data ";
                    Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                    toast.show();

                } else {

                    int DueDay = Integer.valueOf(s_d_Due_edit);
                    int DueMonth = Integer.valueOf(s_m_Due_edit);
                    int DueYear = Integer.valueOf(s_y_Due_edit);

                    try {
                        borrowingDate = LocalDate.now();
                    } catch (DateTimeException e) {
                        TOAST_TEXT = "Invalid Borrowing Date" + e.getMessage();
                        Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                        toast.show();
                        exceptionOccurred = true;
                    }
                    try {
                        dueDate = LocalDate.of(DueYear, DueMonth, DueDay);
                    } catch (DateTimeException e) {
                        TOAST_TEXT = "Invalid Due date" + e.getMessage();
                        Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                        toast.show();
                        exceptionOccurred = true;
                    }
                    if (!exceptionOccurred) {
                        if (borrowingDate.isAfter(dueDate)) {
                            TOAST_TEXT = "Invalid Date:Borrowing Date Should Be Smaller Than Due Date";
                            Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else if (borrowingDate.isEqual(dueDate)) {
                            TOAST_TEXT = "Invalid Date:due Date Should Be bigger Than current Date";
                            Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                         else {
                            DataBaseHelper dataBaseHelper = new
                                    DataBaseHelper(BorrowBookActivity.this, "Library", null, 1);
                            Cursor cursorBorrowing = dataBaseHelper.getAllBorrowingsByBookId(Integer.valueOf(s_book_Id));
                            Cursor cursorBorrower = dataBaseHelper.getBorrowersById(Integer.valueOf(s_borrower_Id));
                            Cursor cursorBook = dataBaseHelper.getBookById(Integer.valueOf(s_book_Id));

                            if (!cursorBook.moveToFirst()) {
                                TOAST_TEXT = "This Book Id not Exist";
                                Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                                toast.show();
                            } else if (!cursorBorrower.moveToFirst()) {
                                TOAST_TEXT = "This Member Id not Exist";
                                Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                                toast.show();
                            } else if (!cursorBorrowing.moveToFirst()) {
                                cursorBorrowing.moveToPosition(0);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                String formattedDate1 = borrowingDate.format(formatter);
                                String formattedDate2 = dueDate.format(formatter);
                                dataBaseHelper.insertBorrowing(new Borrowing(Integer.valueOf(s_borrower_Id), Integer.valueOf(s_book_Id), formattedDate1, "---", formattedDate2));
                                TOAST_TEXT = "Borrowed Successful";
                                Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(BorrowBookActivity.this, ListAllActivelyBorrowedBooksActivity.class);
                                BorrowBookActivity.this.startActivity(intent);
                                finish();
                            } else {
                                cursorBorrowing.moveToPosition(-1);
                                cursorBorrower.moveToPosition(-1);
                                while (cursorBorrowing.moveToNext()) {
                                    String[] dueDateParts = cursorBorrowing.getString(5).split("/");
                                    int dueDay1 = Integer.parseInt(dueDateParts[0]);
                                    int dueMonth1 = Integer.parseInt(dueDateParts[1]);
                                    int dueYear1 = Integer.parseInt(dueDateParts[2]);
                                    dueDateChecker = LocalDate.of(dueYear1, dueMonth1, dueDay1);

                                    String[] borrowingDateParts = cursorBorrowing.getString(3).split("/");
                                    int borrowingDay1 = Integer.parseInt(borrowingDateParts[0]);
                                    int borrowingMonth1 = Integer.parseInt(borrowingDateParts[1]);
                                    int borrowingYear1 = Integer.parseInt(borrowingDateParts[2]);
                                    borrowingDateChecker = LocalDate.of(borrowingYear1, borrowingMonth1, borrowingDay1);

                                    if (!cursorBorrowing.getString(4).equalsIgnoreCase("---")) {
                                        String[] returnedDateParts = cursorBorrowing.getString(4).split("/");
                                        int returnedDay = Integer.parseInt(returnedDateParts[0]);
                                        int returnedMonth = Integer.parseInt(returnedDateParts[1]);
                                        int returnYear = Integer.parseInt(returnedDateParts[2]);
                                        returnedDate = LocalDate.of(returnYear, returnedMonth, returnedDay);
                                        dateChecker = returnedDate;
                                    } else {
                                        dateChecker = dueDateChecker;
                                    }
                                    boolean available = dueDate.isBefore(borrowingDateChecker) || borrowingDate.isAfter(dateChecker)||borrowingDate.isEqual(dateChecker);
                                    if (!available) {
                                        check = false;
                                    }

                                }
                                //the book is available for borrowing
                                if (check) {
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    String formattedDate1 = borrowingDate.format(formatter);
                                    String formattedDate2 = dueDate.format(formatter);

                                    dataBaseHelper.insertBorrowing(new Borrowing(Integer.valueOf(s_borrower_Id), Integer.valueOf(s_book_Id), formattedDate1, "---", formattedDate2));
                                    TOAST_TEXT = "Borrowed Successful";
                                    Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(BorrowBookActivity.this, ListAllActivelyBorrowedBooksActivity.class);
                                    BorrowBookActivity.this.startActivity(intent);
                                    finish();
                                } else {
                                    TOAST_TEXT = "The book you try to borrow is busy now ...try another book :)";
                                    Toast toast = Toast.makeText(BorrowBookActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }


                        }
                    }
                }

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        // Create an explicit intent to go to the main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        // Finish the current activity
        finish();
        return true;
    }
}