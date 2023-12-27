package com.example.comupershop;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class FragmentList extends Fragment {

    private View view;
    private TableLayout tableLayout;

    private MyDatabaseHelper databaseHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);

        tableLayout = view.findViewById(R.id.tableLayout);
        Bundle bundle =  getArguments();
        databaseHelper = new MyDatabaseHelper(requireContext());
        Cursor cursor = databaseHelper.readAllData();

        if (bundle != null)
        {

            int n = cursor.getCount();
            cursor.moveToFirst();

            for (int i = 0; i < n; i++)
            {
                String name = cursor.getString(1);
                int price = cursor.getInt(2);
                addTableRow(name, price);
                cursor.moveToNext();
            }
        }

        return view;
    }


    private void addTableRow(String name, int price)
    {
        TableRow row = new TableRow(requireActivity());
        TableRow.LayoutParams rowp = new TableRow.LayoutParams(-2, -2);
        row.setLayoutParams(rowp);
        row.setBackgroundColor(Color.WHITE);

        TextView tvName = new TextView(requireActivity());
        TableRow.LayoutParams ll = new TableRow.LayoutParams(-2, -2);
        ll.setMargins(0, 1, 0, 0);
        tvName.setLayoutParams(ll);


        if (name.length() > 10) {
            tvName.setText(DownRow(name));
        }
        else
        {
            tvName.setText(name);
        }
        tvName.setTextColor(Color.BLACK);
        tvName.setTextSize(26);
        tvName.setGravity(Gravity.CENTER);


        TextView tvPrice = new TextView(requireActivity());
        tvPrice.setLayoutParams(ll);
        tvPrice.setTextColor(Color.BLACK);
        tvPrice.setTextSize(26);
        tvPrice.setGravity(Gravity.CENTER);
        tvPrice.setText("" + price);


        row.addView(tvPrice);
        row.addView(tvName);
        tableLayout.addView(row);
    }

    public String DownRow(String name)
    {
        int index = 0;
        String str = "";

        for (int i = 0; i < name.length(); i++)
        {
            index++;
            str += name.charAt(i);
            if (index >= 10)
            {
                str += "\n";
                index = 0;
            }
        }
        return str;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (databaseHelper != null)
        {
            databaseHelper.deleteAllData();
        }

    }
}