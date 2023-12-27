package com.example.comupershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        if (databaseHelper != null)
        {
            databaseHelper.deleteAllData();
        }
    }
}