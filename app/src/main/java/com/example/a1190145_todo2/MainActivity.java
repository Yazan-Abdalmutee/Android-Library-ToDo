package com.example.a1190145_todo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);

        Button list_Available_Books_Button = findViewById(R.id.availableBooks_button);
        Button list_Library_Members_Button = findViewById(R.id.libraryMembers_button);
        Button list_Borrowed_Books_Button = findViewById(R.id.borowedBooks_button);
        Button borrow_Books_Button = findViewById(R.id.borrowBooks_button);
        Button insert = findViewById(R.id.insert_button);
        Button clear = findViewById(R.id.clear_button);

        list_Available_Books_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListAvailableBooksActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        list_Library_Members_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListAllActivelyLibraryMembersActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        list_Borrowed_Books_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListAllActivelyBorrowedBooksActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        borrow_Books_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BorrowBookActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseClear();
                dataBaseInsertion();
                String TOAST_TEXT = "Data Inserted Successfully";
                Toast toast = Toast.makeText(MainActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseClear();
                String TOAST_TEXT = "Data Cleared Successfully";
                Toast toast = Toast.makeText(MainActivity.this, TOAST_TEXT, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.close_button) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dataBaseInsertion() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this, "Library", null, 1);

        dataBaseHelper.insertBorrower(new Borrower("Yazan", "Shrouf", "27/12/2024"));
        dataBaseHelper.insertBorrower(new Borrower("Ahmad", "Sami", "4/11/2023"));
        dataBaseHelper.insertBorrower(new Borrower("Khaled", "Kareem", "11/11/2025"));

        dataBaseHelper.insertBorrowing(new Borrowing(1, 1, "1/1/2020", "12/1/2020", "12/1/2022"));
        dataBaseHelper.insertBorrowing(new Borrowing(2, 1, "1/11/2023", "---", "18/1/2024"));
        dataBaseHelper.insertBorrowing(new Borrowing(3, 1, "1/1/2022", "1/8/2023", "1/8/2023"));
        dataBaseHelper.insertBorrowing(new Borrowing(1, 2, "1/11/2023", "2/11/2023", "2/11/2023"));
        dataBaseHelper.insertBorrowing(new Borrowing(2, 2, "1/1/2022", "8/1/2022", "12/1/2022"));
        dataBaseHelper.insertBorrowing(new Borrowing(3, 2, "8/8/2020", "20/8/2020", "20/8/2020"));
        dataBaseHelper.insertBorrowing(new Borrowing(1, 3, "11/8/2022", "17/8/2022", "22/8/2022"));
        dataBaseHelper.insertBorrowing(new Borrowing(2, 3, "20/6/2022", "---", "22/6/2024"));
        dataBaseHelper.insertBorrowing(new Borrowing(3, 3, "1/5/2021", "1/6/2021", "1/6/2021"));

        dataBaseHelper.insertBook(new Book("The World", "Conan", "16/4/1983"));
        dataBaseHelper.insertBook(new Book("Animals", "Holmes", "22/2/1990"));
        dataBaseHelper.insertBook(new Book("Water", "Watson", "11/4/1880"));
        //this book  available for borrowing at every time (u can test it for borrow book page) ...
        dataBaseHelper.insertBook(new Book("English", "Yohan", "15/2/1974"));
    }

    public void dataBaseClear() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this, "Library", null, 1);
        dataBaseHelper.clearTables();
    }
}
