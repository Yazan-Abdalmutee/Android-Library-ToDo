package com.example.a1190145_todo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.time.LocalDate;

public class ListAllActivelyLibraryMembersActivity extends AppCompatActivity {
    LocalDate currentDate = LocalDate.now();
    int year = currentDate.getYear();
    int month = currentDate.getMonthValue();
    int day = currentDate.getDayOfMonth();
    LinearLayout linearLayout;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_actively_library_members);
        linearLayout=findViewById(R.id.linearLayout);
        Toolbar toolbar = findViewById(R.id.members_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new
                DataBaseHelper(ListAllActivelyLibraryMembersActivity.this, "Library", null, 1);
        Cursor cursor = dataBaseHelper.getAllBorrowers();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            String[] dateParts = cursor.getString(3).split("/");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            LocalDate inputDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();

            if (inputDate.isAfter(currentDate)) {
                if (counter == 0) {
                    linearLayout.removeAllViews();
                    counter++;
                }
                TextView textView = new TextView(ListAllActivelyLibraryMembersActivity.this);
                textView.setText(
                        "Id= " + cursor.getString(0)
                                + "\nFirstName= " + cursor.getString(1)
                                + "\nLastName= " + cursor.getString(2)
                                + "\nMemberShipExpiryDate= " + cursor.getString(3)
                                + "\n\n"
                );
                linearLayout.addView(textView);
            }
        }
    }
}
