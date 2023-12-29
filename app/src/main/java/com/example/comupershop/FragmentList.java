package com.example.comupershop;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class FragmentList extends Fragment {

    private View view;
    private TableLayout tableLayout;

    private MyDatabaseHelper databaseHelper;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);

        tableLayout = view.findViewById(R.id.tableLayout);
        databaseHelper = new MyDatabaseHelper(requireContext());
        cursor = databaseHelper.readAllData();


        int n = cursor.getCount();
        cursor.moveToFirst();

        for (int i = 0; i < n; i++)
        {
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            addTableRow(name, price, Integer.parseInt(cursor.getString(0)));
            cursor.moveToNext();
        }


        return view;
    }


    private void addTableRow(String namet, int pricet, int id)
    {
        // Check if the TableRow with the given ID already exists
        TableRow existingRow = tableLayout.findViewWithTag(id);

        if (existingRow != null)
        {
            if(pricet !=-100)
            {
                TextView tvName = existingRow.findViewWithTag("name");
                TextView tvPrice = existingRow.findViewWithTag("price");
                if (namet.length() > 5)
                {
                    tvName.setText(DownRow(namet));
                }
                else
                {
                    tvName.setText(namet);
                }
                tvPrice.setText(String.valueOf(pricet));
            }
            else
            {
                tableLayout.removeView(tableLayout.findViewWithTag(id));
            }
        }
        else
        {
            TableRow row = new TableRow(requireActivity());
            row.setTag(id); // Set the tag to the ID
            TableRow.LayoutParams rowp = new TableRow.LayoutParams(-2, -2);
            row.setLayoutParams(rowp);
            row.setBackgroundColor(Color.WHITE);

            TextView tvName = new TextView(requireActivity());
            tvName.setTag("name"); // Set the tag to identify the TextView
            TableRow.LayoutParams ll = new TableRow.LayoutParams(-2, -2);
            ll.setMargins(0, 1, 0, 0);
            tvName.setLayoutParams(ll);


            if (namet.length() > 5) {
                tvName.setText(DownRow(namet));
            } else {
                tvName.setText(namet);
            }
            tvName.setTextColor(Color.BLACK);
            tvName.setTextSize(26);
            tvName.setGravity(Gravity.CENTER);

            Button update = new Button(requireActivity());
            update.setId(id);
            update.setTextColor(Color.BLACK);
            update.setTextSize(15);
            update.setGravity(Gravity.CENTER);
            update.setText("עדכן");

            TableRow.LayoutParams updateLayoutParams = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT); // Set the width as needed
            update.setLayoutParams(updateLayoutParams);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog(update.getId(), namet, pricet);
                }
            });

            Button delete = new Button(requireActivity());
            delete.setId(id);
            delete.setTextColor(Color.BLACK);
            delete.setTextSize(15);
            delete.setGravity(Gravity.CENTER);
            delete.setText("מחק");

            TableRow.LayoutParams deleteLayoutParams = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT); // Set the width as needed
            delete.setLayoutParams(deleteLayoutParams);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    databaseHelper.deleteOneRow(String.valueOf(id));
                    addTableRow("",-100,id);
                }
            });

            TextView tvPrice = new TextView(requireActivity());
            tvPrice.setTag("price"); // Set the tag to identify the TextView
            tvPrice.setLayoutParams(ll);
            tvPrice.setTextColor(Color.BLACK);
            tvPrice.setTextSize(26);
            tvPrice.setGravity(Gravity.CENTER);
            tvPrice.setText("" + pricet);

            row.addView(delete);
            row.addView(update);
            row.addView(tvPrice);
            row.addView(tvName);
            tableLayout.addView(row);
        }
    }

    public String DownRow(String name)
    {
        int index = 0;
        String str = "";

        for (int i = 0; i < name.length(); i++)
        {
            index++;
            str += name.charAt(i);
            if (index >= 5)
            {
                str += "\n";
                index = 0;
            }
        }
        return str;
    }


    private void customDialog(int id,String name, int price)
    {
        Dialog dialog = new Dialog(requireActivity());
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.custom_dialog);
        EditText upname = dialog.findViewById(R.id.etupName);
        EditText upprice = dialog.findViewById(R.id.etupPrice);
        Button btnSend = dialog.findViewById(R.id.btnUpdate);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        upname.setText(name);
        upprice.setText(String.valueOf(price));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.updateData(String.valueOf(id),upname.getText().toString(), Integer.parseInt(upprice.getText().toString()));
                addTableRow(upname.getText().toString(), Integer.parseInt(upprice.getText().toString()),id);
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}