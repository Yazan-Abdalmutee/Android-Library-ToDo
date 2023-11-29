package com.example.a1190145_todo2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ListAllActivelyBorrowedBooksActivity extends AppCompatActivity {
    String TOAST_TEXT;
    LinearLayout linearLayout;
    LocalDate dueDate, borrowingDate, returnedDate, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_actively_borrowed_books);
        Toolbar toolbar = findViewById(R.id.borrowedBooks_toolbar);
        linearLayout = findViewById(R.id.linearLayout);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button search = findViewById(R.id.btton_search);
        EditText edit_Id = findViewById(R.id.edit_Id);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ListAllActivelyBorrowedBooksActivity.this, "Library", null, 1);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringId = edit_Id.getText().toString();

                if (stringId.isEmpty()) {
                    TOAST_TEXT = "Please enter the ID: ";
                    Toast toast = Toast.makeText(ListAllActivelyBorrowedBooksActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    linearLayout.removeAllViews();
                    Cursor cursorBorrowings = dataBaseHelper.getAllBorrowingsById(Integer.parseInt(stringId));

                    if (!cursorBorrowings.moveToFirst()) {
                        TOAST_TEXT = "No member has this ID ";
                        Toast toast = Toast.makeText(ListAllActivelyBorrowedBooksActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        cursorBorrowings.moveToPosition(-1);
                        while (cursorBorrowings.moveToNext()) {

                            Cursor cursorMember = dataBaseHelper.getBorrowersById(Integer.valueOf(cursorBorrowings.getString(1)));
                            Cursor cursorBook = dataBaseHelper.getBookById(Integer.valueOf(cursorBorrowings.getString(2)));

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

                            if (!cursorBorrowings.getString(4).equalsIgnoreCase("---")) {
                                String[] returnedDateParts = cursorBorrowings.getString(4).split("/");
                                int returnedDay = Integer.parseInt(returnedDateParts[0]);
                                int returnedMonth = Integer.parseInt(returnedDateParts[1]);
                                int returnYear = Integer.parseInt(returnedDateParts[2]);
                                returnedDate = LocalDate.of(returnYear, returnedMonth, returnedDay);
                                date = returnedDate;
                            } else {
                                date = dueDate;
                            }
                            boolean available = date.isAfter(LocalDate.now());

                            if (available) {
                                cursorBook.moveToPosition(0);
                                cursorMember.moveToPosition(0);
                                Button button = new Button(ListAllActivelyBorrowedBooksActivity.this);
                                button.setLayoutParams(new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ) {{
                                    setMargins(80, 60, 0, 0); // Set the left margin
                                }});
                                button.setText("Return Book");
                                button.setId(Integer.valueOf(cursorBorrowings.getString(0)));

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        View tView = linearLayout.findViewById((button.getId() * -1) - 1);
                                        linearLayout.removeView(tView);
                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(ListAllActivelyBorrowedBooksActivity.this, "Library", null, 1);
                                        LocalDate returnedDate1=LocalDate.now();
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        String formattedDate1 = returnedDate1.format(formatter);
                                        dataBaseHelper.updateBorrowingDataById(button.getId(),formattedDate1);

                                    }
                                });

                                LinearLayout linear = new LinearLayout(ListAllActivelyBorrowedBooksActivity.this);
                                linear.setOrientation(LinearLayout.HORIZONTAL);
                                TextView textView = new TextView(ListAllActivelyBorrowedBooksActivity.this);
                                textView.setText(
                                        "Member Id: " + cursorBorrowings.getString(1)
                                                + "\nMember Name: " + cursorMember.getString(1) + " " + cursorMember.getString(2)
                                                + "\nBook Name: " + cursorBook.getString(1)
                                                +"\nBook ID:"+cursorBook.getString(0)
                                                + "\nBorrowing Date: " + cursorBorrowings.getString(3)
                                                + "\nDue Date: " + cursorBorrowings.getString(5)
                                                + "\n\n"
                                );
                                linear.addView(textView);
                                linear.addView(button);
                                linear.setId((Integer.valueOf((cursorBorrowings.getString(0))) * -1) - 1);
                                linearLayout.addView(linear);

                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        return true;
    }
}
