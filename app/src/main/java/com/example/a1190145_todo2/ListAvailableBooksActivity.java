package com.example.a1190145_todo2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;

public class ListAvailableBooksActivity extends AppCompatActivity {
    String TOAST_TEXT = "";
    LocalDate firstDate, endDate;
    LinearLayout linearLayout;
    boolean exceptionOccurred = false;

    //array of 10 books id for checking if the book is not busy at the input range
    boolean[] myArray = new boolean[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available_books);
        Arrays.fill(myArray, true);
        linearLayout = findViewById(R.id.linearLayout);
        Toolbar toolbar = findViewById(R.id.availabeBooks_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //d:day m:month y:year
        EditText first_day = findViewById(R.id.d_first_edit);
        EditText first_month = findViewById(R.id.m_first_edit);
        EditText first_year = findViewById(R.id.y_first_edit);
        Button search = findViewById(R.id.search_button);

        EditText end_day = findViewById(R.id.d_end_edit);
        EditText end_month = findViewById(R.id.m_end_edit);
        EditText end_year = findViewById(R.id.y_end_edit);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Arrays.fill(myArray, true);
                exceptionOccurred = false;
                linearLayout.removeAllViews();
                String stringFirstDay = first_day.getText().toString();
                String stringFirstMonth = first_month.getText().toString();
                String stringFirstYear = first_year.getText().toString();

                String stringEndDay = end_day.getText().toString();
                String stringEndMonth = end_month.getText().toString();
                String stringEndYear = end_year.getText().toString();

                if (stringFirstDay.isEmpty() || stringFirstMonth.isEmpty() || stringFirstYear.isEmpty() || stringEndDay.isEmpty() || stringEndMonth.isEmpty() || stringEndYear.isEmpty()) {
                    TOAST_TEXT = "Please fill all the data";
                    Toast toast = Toast.makeText(ListAvailableBooksActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int firstDay = Integer.valueOf(stringFirstDay);
                    int firstMonth = Integer.valueOf(stringFirstMonth);
                    int firstYear = Integer.valueOf(stringFirstYear);

                    int endDay = Integer.valueOf(stringEndDay);
                    int endMonth = Integer.valueOf(stringEndMonth);
                    int endYear = Integer.valueOf(stringEndYear);

                    //check if the first range is valid
                    try {
                        firstDate = LocalDate.of(firstYear, firstMonth, firstDay);
                    } catch (DateTimeException e) {
                        TOAST_TEXT = "Invalid date in first Year: " + e.getMessage();
                        Toast toast = Toast.makeText(ListAvailableBooksActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                        toast.show();
                        exceptionOccurred = true;
                    }

                    //check if the first range is valid
                    try {
                        endDate = LocalDate.of(endYear, endMonth, endDay);
                    } catch (DateTimeException e) {
                        TOAST_TEXT = "Invalid date in end Year: " + e.getMessage();
                        Toast toast = Toast.makeText(ListAvailableBooksActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                        toast.show();
                        exceptionOccurred = true;
                    }

                    if (!exceptionOccurred) {
                        if (firstDate.isAfter(endDate)||firstDate.isEqual(endDate)) {
                            TOAST_TEXT = "Invalid Date:First Year Should Be Smaller Than End Year";
                            Toast toast = Toast.makeText(ListAvailableBooksActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            DataBaseHelper dataBaseHelper = new DataBaseHelper(ListAvailableBooksActivity.this, "Library", null, 1);
                            Cursor cursorBorrowings = dataBaseHelper.getAllBorrowings();
                            cursorBorrowings.moveToPosition(-1);
                            LocalDate date, returnedDate, dueDate, borrowingDate;

                            while (cursorBorrowings.moveToNext()) {
                                //splitting the due date for first book borrowing and get its due-returned-borrowing date
                                String[] dueDateParts = cursorBorrowings.getString(5).split("/");
                                int dueDay = Integer.parseInt(dueDateParts[0]);
                                int dueMonth = Integer.parseInt(dueDateParts[1]);
                                int dueYear = Integer.parseInt(dueDateParts[2]);
                                dueDate = LocalDate.of(dueYear, dueMonth, dueDay);

                                String[] borrowingDateParts = cursorBorrowings.getString(3).split("/");
                                int borrowingDay = Integer.parseInt(borrowingDateParts[0]);
                                int borrowingMonth = Integer.parseInt(borrowingDateParts[1]);
                                int borrowingYear = Integer.parseInt(borrowingDateParts[2]);
                                borrowingDate = LocalDate.of(borrowingYear, borrowingMonth, borrowingDay);

                                //no returned data in this case
                                if (!cursorBorrowings.getString(4).equalsIgnoreCase("---")) {
                                    String[] returnedDateParts = cursorBorrowings.getString(4).split("/");
                                    int returnedDay = Integer.parseInt(returnedDateParts[0]);
                                    int returnedMonth = Integer.parseInt(returnedDateParts[1]);
                                    int returnYear = Integer.parseInt(returnedDateParts[2]);
                                    returnedDate = LocalDate.of(returnYear, returnedMonth, returnedDay);
                                    date = returnedDate;
                                } else {
                                    //date  ..is the smallest value between returned an due date
                                    date = dueDate;
                                }
                                //if the book due date before the first range or after te end range ...available book in that range
                                boolean available = date.isBefore(firstDate) || borrowingDate.isAfter(endDate)||date.isEqual(firstDate)||borrowingDate.isEqual(endDate);
                                int bookId = Integer.parseInt(cursorBorrowings.getString(2));
                                if (!available) {
                                    myArray[bookId] = false;
                                }
                            }

                            for (int i = 0; i < myArray.length; i++) {
                                if (myArray[i]) {
                                    Cursor cursorBooks = dataBaseHelper.getBookById(i);
                                    if (cursorBooks.moveToFirst()) {
                                        TextView textView = new TextView(ListAvailableBooksActivity.this);
                                        textView.setText(
                                                "Id= " + cursorBooks.getString(0)
                                                        + "\nTitle= " + cursorBooks.getString(1)
                                                        + "\nAuthor= " + cursorBooks.getString(2)
                                                        + "\nPublication Date= " + cursorBooks.getString(3)
                                                        + "\n\n"
                                        );
                                        linearLayout.addView(textView);
                                    }
                                    cursorBooks.close();
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
