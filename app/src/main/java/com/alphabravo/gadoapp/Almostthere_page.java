package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Almostthere_page extends AppCompatActivity {

    private ImageView backbutton, proceed, settings;
    private EditText amount1;
    private TextView contactus;
    MyDatabaseHelper myDB;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    //press again to exit
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    //press again to exit

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.almosttherepage);

        proceed = (ImageView) findViewById(R.id.proceedbtn);
        amount1 = (EditText) findViewById(R.id.amountEditText01);
        backbutton = (ImageView) findViewById(R.id.backbtn);
        String useramount = getIntent().getStringExtra("keytxtwallet");
        amount1.setText(useramount);
        contactus = (TextView) findViewById(R.id.contactus);
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        settings = findViewById(R.id.settings);
        myDB = new MyDatabaseHelper(this);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsCheck();
            }
        });



        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialAlertDialogBuilder.setTitle("Send us Feedback, Suggestions, or Concerns.");
                materialAlertDialogBuilder.setMessage("gezreelwazrcon@gmail.com\n" + "villarizaced@gmail.com\n");
                materialAlertDialogBuilder.show();
            }
        });




        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                proceed.setEnabled(true);
                String txtProceed = amount1.getText().toString();
                int intWallet = Integer.parseInt(txtProceed);
                if (TextUtils.isEmpty(txtProceed)) {
                    Toast.makeText(Almostthere_page.this, "Enter your Budget to Proceed.", Toast.LENGTH_SHORT).show();
                }else if (intWallet == 0) {
                    Toast.makeText(Almostthere_page.this, "Budget must be greater than 0.", Toast.LENGTH_SHORT).show();
                }else {
                    myDB.resetLocalDatabase();
                    proceedUser(txtProceed);
                }

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInputPage();
            }
        });


    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsPage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void goToInputPage() {
        Intent intent = new Intent(this, WelcomeuserPage.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void proceedUser(String txtProceed) {
        String budget = amount1.getText().toString();
        if (budget.matches("")) {
            Toast.makeText(this, "There's no Value!", Toast.LENGTH_SHORT).show();
        }else {
            MyDatabaseHelper myDB = new MyDatabaseHelper(Almostthere_page.this);
            myDB.addBook(amount1.getText().toString().trim(),
                    amount1.getText().toString().trim());

        Intent intent = new Intent(this, InputPage.class);
        intent.putExtra("keytxtproceed", txtProceed);
        startActivity(intent);
        overridePendingTransition(0, 0);
        }
    }

    private void settingsCheck(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount()==0)
        {
            Toast.makeText(Almostthere_page.this, "Input a daily budget first!", Toast.LENGTH_SHORT).show();
        }else{
            openSettings();
        }
    }
}



